@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.yarn.task.YarnInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.docusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusWriteHeadingIdsTask : YarnTask() {

    @Input public val files: Property<String?> = objects.property()
    @Input public val maintainCase: Property<Boolean?> = objects.property()
    @Input public val overwrite: Property<Boolean?> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusWriteHeadingIdsTask"
        private const val Files = "--files"
        private const val MaintainCase = "--maintain-case"
        private const val Overwrite = "--overwrite"

        internal fun Project.registerDocusaurusWriteHeadingIdsTask() {
            tasks.maybeRegisterLazily<DocusaurusWriteHeadingIdsTask>(NAME) { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    preCommand = "run",
                    command = "write-heading-ids",
                    arguments =
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
