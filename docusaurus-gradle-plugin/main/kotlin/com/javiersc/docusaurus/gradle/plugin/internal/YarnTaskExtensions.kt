package com.javiersc.docusaurus.gradle.plugin.internal

import com.github.gradle.node.yarn.task.YarnTask
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

internal inline fun <reified T : Any?> YarnTask.nullConvention(): Property<T> =
    objects.property<T>().convention(null as T?)

internal fun YarnTask.yarnCommand(
    command: String,
    values: List<Property<String>> = emptyList(),
    properties: Map<String, Property<*>>,
) {
    yarnCommand.set(providers.provider { buildArguments(command, values, properties) })
}
