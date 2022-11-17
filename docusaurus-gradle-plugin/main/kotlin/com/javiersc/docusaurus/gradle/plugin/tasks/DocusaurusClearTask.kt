@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project

public abstract class DocusaurusClearTask : YarnTask() {

    init {
        group = "documentation"
    }

    public companion object {
        public const val NAME: String = "docusaurusClear"

        internal fun Project.registerDocusaurusClearTask(docusaurusExtension: DocusaurusExtension) {
            tasks.maybeRegisterLazily<DocusaurusClearTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand.set(listOf("run", "clear"))
            }
        }
    }
}
