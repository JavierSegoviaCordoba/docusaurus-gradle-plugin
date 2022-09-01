package com.javiersc.docusaurus.gradle.plugin.internal

internal fun String.cliLink(anchorLink: String) =
    "$this\nMore info at $docusaurusCliUrl#$anchorLink"

internal const val docusaurusCliUrl = "https://docusaurus.io/docs/cli"
