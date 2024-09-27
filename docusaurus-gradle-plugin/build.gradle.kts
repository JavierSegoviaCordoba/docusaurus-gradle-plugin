hubdle {
    config {
        analysis()
        coverage()
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
                serialization()
            }

            main {
                dependencies {
                    implementation(libs.github.nodeGradle.node)
                }
            }

            testFunctional()
        }
    }
}
