plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        explicitApi()
        publishing()
    }

    kotlin {
        gradle {
            plugin {
                main { dependencies { implementation(libs.github.nodeGradle.node) } }

                gradlePlugin {
                    plugins {
                        create("com.javiersc.docusaurus") {
                            id = "com.javiersc.docusaurus"
                            displayName = "Docusaurus"
                            description = "Create documentation using Docusaurus"
                            implementationClass =
                                "com.javiersc.docusaurus.gradle.plugin.DocusaurusPlugin"
                        }
                    }
                }
            }
        }
    }
}
