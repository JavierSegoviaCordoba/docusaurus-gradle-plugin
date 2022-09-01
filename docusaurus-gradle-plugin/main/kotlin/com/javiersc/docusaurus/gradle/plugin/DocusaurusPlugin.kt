package com.javiersc.docusaurus.gradle.plugin

import com.github.gradle.node.NodeExtension
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
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.the

public class DocusaurusPlugin : Plugin<Project> {

    public override fun Project.apply() {
        val docusaurusExtension: DocusaurusExtension = extensions.create("docusaurus")

        pluginManager.apply("com.github.node-gradle.node")

        configNode(docusaurusExtension)

        registerDocusaurusKillPortTask()
        registerDocusaurusCheckPackageJsonTask(docusaurusExtension)
        registerDocusaurusCreateTask()
        registerDocusaurusStartTask(docusaurusExtension)
        registerDocusaurusBuildTask(docusaurusExtension)
        registerDocusaurusSwizzleTask(docusaurusExtension)
        registerDocusaurusDeployTask(docusaurusExtension)
        registerDocusaurusServeTask(docusaurusExtension)
        registerDocusaurusClearTask(docusaurusExtension)
        registerDocusaurusWriteTranslationsTask(docusaurusExtension)
        registerDocusaurusWriteHeadingIdsTask(docusaurusExtension)
        registerDocusaurusVersionTask(version, docusaurusExtension)
    }

    private fun Project.configNode(docusaurusExtension: DocusaurusExtension) {
        the<NodeExtension>().apply {
            download.set(false)
            nodeProjectDir.set(file(docusaurusExtension.directory))
        }
    }
}
