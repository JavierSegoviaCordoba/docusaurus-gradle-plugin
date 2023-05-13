package com.javiersc.docusaurus.gradle.plugin.tasks

import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.kotlin.stdlib.isNotNullNorEmpty
import javax.inject.Inject
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

public abstract class DocusaurusCheckPackageJsonTask
@Inject
constructor(
    objects: ObjectFactory,
) : DefaultTask() {

    @Internal public val name: Property<String> = objects.property()

    @Internal public val packageJson: RegularFileProperty = objects.fileProperty()

    init {
        group = "documentation"
    }

    @TaskAction
    public fun run() {
        val packageJsonFile = packageJson.get().asFile
        if (!packageJsonFile.exists()) {
            packageJsonFile.parentFile.mkdirs()
            packageJsonFile.createNewFile()
            packageJsonFile.writeText(defaultPackageJsonContent)
        }

        try {
            val minimumPackageJson: MinimumPackageJson =
                json.decodeFromString(packageJsonFile.readText())
            check(minimumPackageJson.name.isNotNullNorEmpty()) {
                "`name` in `package.json` must not be null or empty"
            }
            check(minimumPackageJson.version.isNotNullNorEmpty()) {
                "`version` in `package.json` must not be null or empty"
            }
        } catch (_: Throwable) {
            error(invalidPackageJsonMessage)
        }
    }

    public companion object {
        public const val NAME: String = "docusaurusCheckPackageJson"

        internal fun Project.registerDocusaurusCheckPackageJsonTask(
            docusaurusExtension: DocusaurusExtension
        ) {
            tasks.register<DocusaurusCheckPackageJsonTask>(NAME).configure { task ->
                task.name.set(docusaurusExtension.name)
                task.packageJson.set(docusaurusExtension.directory.map { it.file("package.json") })
            }
        }
    }
}

private val DocusaurusCheckPackageJsonTask.defaultPackageJsonContent: String
    get() =
        """ |{
            |  "name": "${name.get()}",
            |  "version": "0.0.0"
            |}
            |
        """
            .trimMargin()

private val DocusaurusCheckPackageJsonTask.invalidPackageJsonMessage: String
    get() =
        "The file `package.json` is not correct or does not contain the minimum properties to be valid:\n\n$defaultPackageJsonContent"

private data class MinimumPackageJson(
    @SerialName("name") val name: String,
    @SerialName("version") val version: String,
)

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
