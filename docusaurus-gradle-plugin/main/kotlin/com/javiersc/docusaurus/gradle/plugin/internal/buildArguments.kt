package com.javiersc.docusaurus.gradle.plugin.internal

import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory

internal fun ProviderFactory.buildListCommand(
    preCommand: String,
    command: String,
    additionalCommands: List<Provider<String>> = emptyList(),
): Provider<List<String>> = provider {
    buildList {
        add(preCommand)
        add(command)
        addAll(additionalCommands.map(Provider<String>::get))
    }
}

internal fun ProviderFactory.buildStringCommand(
    command: String,
    additionalCommands: List<Provider<String>> = emptyList()
): Provider<String> = provider {
    buildList {
            add(command)
            addAll(additionalCommands.map(Provider<String>::get))
        }
        .joinToString(" ")
}

internal fun ProviderFactory.buildCliArguments(
    arguments: Map<String, Provider<*>>
): Provider<List<String>> = provider {
    arguments
        .filter { it.value.orNull != null }
        .map { (key: String, value: Provider<*>) ->
            if (value.get() is Boolean && value.get() == true) key else "$key=${value.get()}"
        }
}
