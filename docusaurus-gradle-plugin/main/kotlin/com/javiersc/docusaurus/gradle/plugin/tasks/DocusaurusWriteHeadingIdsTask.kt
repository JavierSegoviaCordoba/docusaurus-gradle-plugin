@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.npm.task.NpmTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.npmCommand
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusWriteHeadingIdsTask : NpmTask() {

    @Input public val files: Property<String?> = objects.property()
    @Input public val maintainCase: Property<Boolean?> = objects.property()
    @Input public val overwrite: Property<Boolean?> = objects.property()

    init {
        group = "documentation"
    }

    public companion object {
        public const val NAME: String = "docusaurusWriteHeadingIdsTask"
        private const val Files = "--files"
        private const val MaintainCase = "--maintain-case"
        private const val Overwrite = "--overwrite"

        internal fun Project.registerDocusaurusWriteHeadingIdsTask(
            docusaurusExtension: DocusaurusExtension
        ) {
            tasks.maybeRegisterLazily<DocusaurusWriteHeadingIdsTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.npmCommand(
                    command = "write-heading-ids",
                    options =
                        mapOf(
                            Files to task.files,
                            MaintainCase to task.maintainCase,
                            Overwrite to task.overwrite,
                        )
                )
            }
        }
    }
}
