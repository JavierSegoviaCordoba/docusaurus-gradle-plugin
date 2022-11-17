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

public abstract class DocusaurusWriteTranslationsTask : NpmTask() {

    @Input public val locale: Property<String?> = objects.property()
    @Input public val override: Property<Boolean?> = objects.property()
    @Input public val config: Property<String?> = objects.property()
    @Input public val messagePrefix: Property<String?> = objects.property()

    init {
        group = "documentation"
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
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.npmCommand(
                    command = "write-translations",
                    options =
                        mapOf(
                            Locale to task.locale,
                            Override to task.override,
                            Config to task.config,
                        )
                )
            }
        }
    }
}
