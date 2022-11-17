@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusServeTask : YarnTask() {

    @Input public val port: Property<Int?> = objects.property()
    @Input public val dir: Property<String?> = objects.property()
    @Input public val build: Property<Boolean?> = objects.property()
    @Input public val config: Property<String?> = objects.property()
    @Input public val host: Property<String?> = objects.property()
    @Input public val noOpen: Property<Boolean?> = objects.property()

    init {
        group = "documentation"
    }

    public companion object {
        public const val NAME: String = "docusaurusServe"
        private const val Port = "--port"
        private const val Dir = "--dir"
        private const val Build = "--build"
        private const val Config = "--config"
        private const val Host = "--host"
        private const val NoOpen = "--no-open"

        internal fun Project.registerDocusaurusServeTask(docusaurusExtension: DocusaurusExtension) {
            tasks.maybeRegisterLazily<DocusaurusServeTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    command = "serve",
                    properties =
                        mapOf(
                            Port to task.port,
                            Dir to task.dir,
                            Build to task.build,
                            Config to task.config,
                            Host to task.host,
                            NoOpen to task.noOpen,
                        )
                )
            }
        }
    }
}
