@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.yarn.task.YarnInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusVersionTask : YarnTask() {

    @Input
    @Optional
    @Option(option = "version", description = "The version to be tagged")
    public val version: Property<Any> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusVersion"

        internal fun Project.registerDocusaurusVersionTask(
            projectVersion: Any,
            docusaurusExtension: DocusaurusExtension
        ) {
            tasks.maybeRegisterLazily<DocusaurusVersionTask>(NAME) { task ->
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
