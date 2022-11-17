package com.javiersc.docusaurus.gradle.plugin

import com.javiersc.kotlin.stdlib.isNotNullNorBlank

public data class DocusaurusConfig(
    public val title: String,
    public val url: String,
    public val baseUrl: String = "/",
    public val favicon: String = "img/favicon.ico",
    public val onBrokenLinks: String = "throw",
    public val onBrokenMarkdownLinks: String = "warn",
    public val tagline: String =
        "Docusaurus makes it easy to maintain Open Source documentation websites.",
    public val githubConfig: GitHubConfig? = null,
) {

    public data class GitHubConfig(
        public val organizationName: String,
        public val projectName: String,
    ) {
        init {
            check(organizationName.isNotBlank() && projectName.isNotNullNorBlank())
        }
    }

    public data class i18n(
        public val defaultLocale: String = "en",
        public val locales: Set<String> = setOf("en"),
        public val path: String = "i18n",
        public val localeConfigs: Map<String, LocaleConfig> = emptyMap(),
    ) {
        public data class LocaleConfig(
            public val label: String,
            public val direction: String,
            public val htmlLang: String,
            public val calendar: String,
            public val path: String,
        )
    }
}
