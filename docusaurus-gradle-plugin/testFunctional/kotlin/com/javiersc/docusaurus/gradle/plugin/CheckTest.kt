package com.javiersc.docusaurus.gradle.plugin

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

class CheckTest : GradleTestKitTest() {

    @Test
    fun `check package_json file`() {
        gradleTestKitTest(sandboxPath = "base") {
            gradlew("docusaurusCheckPackageJson")
                .task(":docusaurusCheckPackageJson")
                .shouldNotBeNull()
                .outcome
                .shouldBe(TaskOutcome.SUCCESS)
        }
    }
}
