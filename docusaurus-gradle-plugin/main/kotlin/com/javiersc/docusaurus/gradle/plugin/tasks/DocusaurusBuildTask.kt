@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.yarn.task.YarnInstallTask
import com.github.gradle.node.yarn.task.YarnTask
import com.javiersc.docusaurus.gradle.plugin.docusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.yarnCommand
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusBuildTask : YarnTask() {

    @Input
    @Optional
    @Option(option = "bundle-analyzer", description = BundleAnalyzerDescription)
    public val bundleAnalyzer: Property<Boolean?> = objects.property()

    @Input
    @Optional
    @Option(option = "out-dir", description = OutDirDescription)
    public val outDir: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "config", description = ConfigDescription)
    public val config: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "no-minify", description = NoMinifyDescription)
    public val noMinify: Property<Boolean?> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusBuild"

        private const val BundleAnalyzer = "--bundle-analyzer"
        private const val BundleAnalyzerDescription =
            "Analyze your bundle with the webpack bundle analyzer."

        private const val OutDir = "--out-dir"
        private const val OutDirDescription =
            "The full path for the new output directory, relative to the current workspace."

        private const val Config = "--config"
        private const val ConfigDescription =
            "Path to Docusaurus config file, default to `[siteDir]/docusaurus.config.js`"

        private const val NoMinify = "--no-minify"
        private const val NoMinifyDescription = "Build website without minimizing JS/CSS bundles."

        internal fun Project.registerDocusaurusBuildTask() {
            tasks.maybeRegisterLazily<DocusaurusBuildTask>(NAME) { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    preCommand = "run",
                    command = "build",
                    arguments =
                        mapOf(
                            BundleAnalyzer to task.bundleAnalyzer,
                            OutDir to task.outDir,
                            Config to task.config,
                            NoMinify to task.noMinify,
                        )
                )
            }
        }
    }
}
