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
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusWriteTranslationsTask : YarnTask() {

    @Input public val locale: Property<String?> = objects.property()
    @Input public val override: Property<Boolean?> = objects.property()
    @Input public val config: Property<String?> = objects.property()
    @Input public val messagePrefix: Property<String?> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusWriteTranslations"
        private const val Locale = "--locale"
        private const val Override = "--override"
        private const val Config = "--config"
        private const val MessagePrefix = "--messagePrefix"

        internal fun Project.registerDocusaurusWriteTranslationsTask(
            docusaurusExtension: DocusaurusExtension
        ) {
            tasks.maybeRegisterLazily<DocusaurusWriteTranslationsTask>(NAME) { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    preCommand = "run",
                    command = "write-translations",
                    arguments =
                        mapOf(
                            Locale to task.locale,
                            Override to task.override,
                            Config to task.config,
                            MessagePrefix to task.messagePrefix,
                        )
                )
            }
        }
    }
}
