@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.npm.task.NpxTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory

public abstract class DocusaurusCreateTask : NpxTask() {

    @InputDirectory public val websiteDirectory: DirectoryProperty = objects.directoryProperty()

    init {
        group = "documentation"
    }

    public companion object {
        public const val NAME: String = "docusaurusCreate"

        internal fun Project.registerDocusaurusCreateTask(
            projectName: String,
            docusaurusExtension: DocusaurusExtension,
        ) {
            tasks.maybeRegisterLazily<DocusaurusCreateTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.websiteDirectory.set(docusaurusExtension.directory)
                task.command.set("create-docusaurus@latest")
                val websitePath = task.websiteDirectory.get().asFile.absolutePath
                task.args.set(providers.provider { listOf(websitePath, "classic") })

                task.doLast {
                    val packageJsonFile =
                        docusaurusExtension.directory.get().asFile.resolve("package.json")
                    val updatedContent =
                        packageJsonFile.readLines().joinToString("\n") { line ->
                            val nameProp = "\"name\":"
                            if (line.filterNot(Char::isWhitespace).startsWith(nameProp))
                                "  $nameProp \"$projectName\","
                            else line
                        }
                    packageJsonFile.writeText(updatedContent)
                }
            }
        }
    }
}
