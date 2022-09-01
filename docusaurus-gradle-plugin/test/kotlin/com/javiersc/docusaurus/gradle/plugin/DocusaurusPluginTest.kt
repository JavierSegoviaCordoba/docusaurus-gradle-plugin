package com.javiersc.docusaurus.gradle.plugin

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import kotlin.test.Test

internal class DocusaurusPluginTest : GradleTest() {

    @Test fun `some test`() = gradleTestKitTest(sandboxPath = "docusaurus/1") {
        println(gradlew("npmInstall").output)
        println(this.projectDir.walkTopDown().toList().joinToString("\n"))
    }
}
