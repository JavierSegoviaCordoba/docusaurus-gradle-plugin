@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.yarn.task.YarnInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.docusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

public abstract class DocusaurusVersionTask : YarnTask() {

    @get:Input
    @get:Optional
    @get:Option(option = "version", description = "The version to be tagged")
    public val version: Property<Any> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusVersion"

        internal fun Project.registerDocusaurusVersionTask(projectVersion: Any) {
            tasks.register<DocusaurusVersionTask>(NAME).configure { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.version.set(projectVersion)

                val taggedVersion = task.version.map(Any::toString)
                val additionalDocsVersionCommand = providers.provider { "docs:version" }

                task.yarnCommand(
                    preCommand = "run",
                    command = "docusaurus",
                    additionalCommands = listOf(additionalDocsVersionCommand, taggedVersion),
                )
            }
        }
    }
}
