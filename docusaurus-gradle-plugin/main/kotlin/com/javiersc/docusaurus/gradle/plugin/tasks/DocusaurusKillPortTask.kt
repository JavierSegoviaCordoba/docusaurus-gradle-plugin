package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpxTask
import com.javiersc.docusaurus.gradle.plugin.internal.npxCommand
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

public abstract class DocusaurusKillPortTask : NpxTask() {

    @get:Input
    @get:Option(option = "port", description = PortDescription)
    public val port: Property<String> = objects.property()

    init {
        group = "other"
        description = "Kill a process running on a given port."
    }

    public companion object {
        public const val NAME: String = "docusaurusKillPort"

        private const val PortDescription: String = "The port to kill"

        internal fun Project.registerDocusaurusKillPortTask() {
            tasks.register<DocusaurusKillPortTask>(NAME).configure { task ->
                task.doFirst { println("Killing process running on port ${task.port.get()}") }

                task.port.set("3000")

                task.npxCommand(command = "kill-port", additionalCommands = listOf(task.port))
            }
        }
    }
}
