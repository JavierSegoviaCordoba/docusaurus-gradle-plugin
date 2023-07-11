package com.javiersc.docusaurus.gradle.plugin

import com.github.gradle.node.NodeExtension
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusAddTask.Companion.registerDocusaurusAddTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusBuildTask.Companion.registerDocusaurusBuildTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusCheckPackageJsonTask.Companion.registerDocusaurusCheckPackageJsonTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusClearTask.Companion.registerDocusaurusClearTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusCreateTask.Companion.registerDocusaurusCreateTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusDeployTask.Companion.registerDocusaurusDeployTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusKillPortTask.Companion.registerDocusaurusKillPortTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusServeTask.Companion.registerDocusaurusServeTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusStartTask.Companion.registerDocusaurusStartTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusSwizzleTask.Companion.registerDocusaurusSwizzleTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusVersionTask.Companion.registerDocusaurusVersionTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusWriteHeadingIdsTask.Companion.registerDocusaurusWriteHeadingIdsTask
import com.javiersc.docusaurus.gradle.plugin.tasks.DocusaurusWriteTranslationsTask.Companion.registerDocusaurusWriteTranslationsTask
import com.javiersc.gradle.plugin.extensions.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

public class DocusaurusPlugin : Plugin<Project> {

    public override fun Project.apply() {
        extensions.create<DocusaurusExtension>("docusaurus")

        pluginManager.apply("com.github.node-gradle.node")

        configNode()

        registerDocusaurusKillPortTask()
        registerDocusaurusCheckPackageJsonTask()
        registerDocusaurusCreateTask()
        registerDocusaurusStartTask()
        registerDocusaurusBuildTask()
        registerDocusaurusSwizzleTask()
        registerDocusaurusDeployTask()
        registerDocusaurusServeTask()
        registerDocusaurusClearTask()
        registerDocusaurusWriteTranslationsTask()
        registerDocusaurusWriteHeadingIdsTask()
        registerDocusaurusVersionTask(version)
        registerDocusaurusAddTask()
    }

    private fun Project.configNode() {
        configure<NodeExtension> { nodeProjectDir.set(docusaurusExtension.directory) }
    }
}
