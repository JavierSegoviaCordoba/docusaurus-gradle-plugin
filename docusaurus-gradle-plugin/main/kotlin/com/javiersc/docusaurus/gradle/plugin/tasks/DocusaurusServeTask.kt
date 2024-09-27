@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.yarn.task.YarnInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.docusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

public abstract class DocusaurusServeTask : YarnTask() {

    @get:Input public val port: Property<Int?> = objects.property()
    @get:Input public val dir: Property<String?> = objects.property()
    @get:Input public val build: Property<Boolean?> = objects.property()
    @get:Input public val config: Property<String?> = objects.property()
    @get:Input public val host: Property<String?> = objects.property()
    @get:Input public val noOpen: Property<Boolean?> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusServe"
        private const val Port = "--port"
        private const val Dir = "--dir"
        private const val Build = "--build"
        private const val Config = "--config"
        private const val Host = "--host"
        private const val NoOpen = "--no-open"

        internal fun Project.registerDocusaurusServeTask() {
            tasks.register<DocusaurusServeTask>(NAME).configure { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    preCommand = "run",
                    command = "serve",
                    arguments =
                        mapOf(
                            Port to task.port,
                            Dir to task.dir,
                            Build to task.build,
                            Config to task.config,
                            Host to task.host,
                            NoOpen to task.noOpen,
                        ),
                )
            }
        }
    }
}
