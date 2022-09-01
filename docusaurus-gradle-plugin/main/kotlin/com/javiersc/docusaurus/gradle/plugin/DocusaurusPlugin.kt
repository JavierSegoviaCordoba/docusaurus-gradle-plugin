package com.javiersc.docusaurus.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class DocusaurusPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.github.node-gradle.node")
    }
}
