@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.npm.task.NpmTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusVersionTask : NpmTask() {

    @Input public val version: Property<Any> = objects.property()

    init {
        group = "documentation"
    }

    public companion object {
        public const val NAME: String = "docusaurusVersion"

        internal fun Project.registerDocusaurusVersionTask(
            projectVersion: Any,
            docusaurusExtension: DocusaurusExtension
        ) {
            tasks.maybeRegisterLazily<DocusaurusVersionTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.version.set(projectVersion)

                task.npmCommand.set(
                    providers.provider {
                        listOf("run", "docusaurus", "docs:version", "${task.version.get()}")
                    }
                )
            }
        }
    }
}
