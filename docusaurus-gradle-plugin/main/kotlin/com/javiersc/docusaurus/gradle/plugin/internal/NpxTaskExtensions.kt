package com.javiersc.docusaurus.gradle.plugin.internal

import com.github.gradle.node.npm.task.NpxTask
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

internal inline fun <reified T : Any?> NpxTask.nullConvention(): Property<T> =
    objects.property<T>().convention(null as T?)

internal fun NpxTask.npxCommand(
    command: String,
    additionalCommands: List<Property<out String?>> = emptyList(),
    arguments: Map<String, Property<*>> = emptyMap(),
) {
    this@npxCommand.command.set(command)
    val args =
        providers.provider {
            buildList {
                addAll(additionalCommands.mapNotNull(Property<out String?>::getOrNull))
                addAll(providers.buildCliArguments(arguments).get())
            }
        }
    this@npxCommand.args.set(args)
}
