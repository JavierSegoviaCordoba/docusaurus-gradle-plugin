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
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusAddTask : YarnTask() {

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    @get:Input
    @get:Option(option = "packageName", description = DocusaurusAddTask.packageName)
    public val packageName: Property<String> = objects.property()

    public companion object {
        public const val NAME: String = "docusaurusAdd"
        public const val packageName: String = "Package name to be added"

        internal fun Project.registerDocusaurusAddTask() {
            tasks.maybeRegisterLazily<DocusaurusAddTask>(NAME) { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    preCommand = "add",
                    command = task.packageName,
                )
            }
        }
    }
}
