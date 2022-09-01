hubdle {
    config {
        documentation {
            api()
        }
        explicitApi()
        publishing()
    }

    kotlin {
        jvm {
            features {
                gradle {
                    plugin {
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

            main {
                dependencies {
                    implementation(libs.github.nodeGradle.node)
                }
            }
        }
    }
}
