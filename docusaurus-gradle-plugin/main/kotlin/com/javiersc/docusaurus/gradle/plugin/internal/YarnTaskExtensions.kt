package com.javiersc.docusaurus.gradle.plugin.internal

import com.github.gradle.node.yarn.task.YarnTask
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.property

internal inline fun <reified T : Any?> YarnTask.nullConvention(): Property<T> =
    objects.property<T>().convention(null as T?)

internal fun YarnTask.yarnCommand(
    preCommand: String,
    command: String,
    additionalCommands: List<Provider<String>> = emptyList(),
    arguments: Map<String, Provider<*>> = emptyMap(),
) {
    yarnCommand.set(providers.buildListCommand(preCommand, command, additionalCommands))
    args.set(providers.buildCliArguments(arguments))
}
