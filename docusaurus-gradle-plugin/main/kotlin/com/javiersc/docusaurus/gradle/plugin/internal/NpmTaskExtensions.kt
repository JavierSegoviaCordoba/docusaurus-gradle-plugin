package com.javiersc.docusaurus.gradle.plugin.internal

import com.github.gradle.node.npm.task.NpmTask
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

internal inline fun <reified T : Any?> NpmTask.nullConvention(): Property<T> =
    objects.property<T>().convention(null as T?)

internal fun NpmTask.npmCommand(
    command: String,
    values: List<Property<String>> = emptyList(),
    options: Map<String, Property<*>>,
) {
    npmCommand.set(providers.provider { buildArguments(command, values, options) })
}
