package com.javiersc.docusaurus.gradle.plugin

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class CreateTest : GradleTestKitTest() {

    @Test
    fun `docusaurus create`() {
        gradleTestKitTest(sandboxPath = "base") {
            println(gradlew("docusaurusCreate", stacktrace()).output)
            println(projectDir.walkTopDown().toList().joinToString("\n"))
            error(projectDir)
        }
    }
}
