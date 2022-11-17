package com.javiersc.docusaurus.gradle.plugin.internal

import org.gradle.api.provider.Property

internal fun buildArguments(
    command: String,
    values: List<Property<String>> = emptyList(),
    options: Map<String, Property<*>>,
): List<String> {
    val optionsAsString: String =
        options
            .filter { it.value.orNull != null }
            .map { (key: String, value: Property<*>) -> "$key ${value.get()}" }
            .joinToString(" ")
    val additionalOptions = if (optionsAsString.isNotEmpty()) "-- $optionsAsString" else ""
    return listOf("run", command) + values.map(Property<String>::get) + additionalOptions.split(" ")
}
