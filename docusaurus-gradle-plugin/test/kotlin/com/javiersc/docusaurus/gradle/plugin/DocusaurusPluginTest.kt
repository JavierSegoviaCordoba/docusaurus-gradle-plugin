package com.javiersc.docusaurus.gradle.plugin

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import kotlin.test.Test

internal class DocusaurusPluginTest : GradleTest() {

    @Test
    fun `check package_json file`() =
        gradleTestKitTest(sandboxPath = "docusaurus/check package_json file") {
            println(gradlew("docusaurusCheckPackageJson").output)
            println(projectDir.resolve(".docs").walkTopDown().toList().joinToString("\n"))
        }

    @Test
    fun `docusaurus create`() =
        gradleTestKitTest(sandboxPath = "docusaurus/check package_json file") {
            println(gradlew("docusaurusCreate").output)
            println(this.projectDir.walkTopDown().toList().joinToString("\n"))
            error(projectDir)
        }

    @Test
    fun `docusaurus create and start`() =
        gradleTestKitTest(sandboxPath = "docusaurus/create and start") {
            println("CREATING...")
            println(gradlew("docusaurusCreate").output)
            println("STARTING...")
            println(gradlew("docusaurusStart").output)
            error(projectDir)
        }

    @Test
    fun `docusaurus create and build`() =
        gradleTestKitTest(sandboxPath = "docusaurus/check package_json file") {
            gradlew("docusaurusCreate")
            gradlew("docusaurusBuild")
            error(projectDir)
        }

    @Test
    fun `docusaurus create and swizzle`() =
        gradleTestKitTest(sandboxPath = "docusaurus/check package_json file") {
            gradlew("docusaurusCreate")
            gradlew("docusaurusSwizzle")
            error(projectDir)
        }

    @Test
    fun `docusaurus create, create and new version`() =
        gradleTestKitTest(sandboxPath = "docusaurus/check package_json file") {
            gradlew("docusaurusCreate")
            gradlew("docusaurusBuild")
            gradlew("docusaurusVersion")
            error(projectDir)
        }
}
