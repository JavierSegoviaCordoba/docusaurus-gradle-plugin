@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.yarn.task.YarnInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.docusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

public abstract class DocusaurusClearTask : YarnTask() {

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusClear"

        internal fun Project.registerDocusaurusClearTask() {
            tasks.register<DocusaurusClearTask>(NAME).configure { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(preCommand = "run", command = "clear")
            }
        }
    }
}
