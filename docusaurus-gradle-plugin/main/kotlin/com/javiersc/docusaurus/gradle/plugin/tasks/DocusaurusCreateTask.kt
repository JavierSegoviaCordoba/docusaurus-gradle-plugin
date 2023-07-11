@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpxTask
import com.javiersc.docusaurus.gradle.plugin.docusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.npxCommand
import com.javiersc.docusaurus.gradle.plugin.internal.nullConvention
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusCreateTask : NpxTask() {

    @Input
    @Option(option = "name", description = NameDescription)
    public val name: Property<String> = objects.property()

    @Input
    @Option(option = "template", description = TemplateDescription)
    public val template: Property<String> = objects.property()

    @Input
    @Optional
    @Option(option = "rootDir", description = RootDirDescription)
    public val rootDir: Property<String?> = nullConvention()

    @Input
    @Optional
    @Option(option = "typescript", description = TypescriptDescription)
    public val typescript: Property<Boolean?> = nullConvention()

    @Input
    @Optional
    @Option(option = "git-strategy", description = GitStrategyDescription)
    public val gitStrategy: Property<String?> = nullConvention()

    @Input
    @Optional
    @Option(option = "package-manager", description = PackageManagerDescription)
    public val packageManager: Property<String?> = nullConvention()

    @Input
    @Optional
    @Option(option = "skip-install", description = SkipInstallDescription)
    public val skipInstall: Property<String?> = nullConvention()

    init {
        group = "documentation"
        description =
            "A scaffolding utility to help you instantly set up a functional Docusaurus app."

        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
    }

    public companion object {
        public const val NAME: String = "docusaurusCreate"

        private const val NameDescription =
            "The name argument will be used as the site's path as well as the name field in the " +
                "created app's package.json."

        private const val TemplateDescription =
            "The template argument can be one of the following: `classic`, `facebook`, a git " +
                "repository URL, or a local path to a template directory."

        private const val RootDirDescription =
            "The rootDir will be used to resolve the absolute path to the site directory."

        private const val Typescript = "--typescript"
        private const val TypescriptDescription =
            "Used when the template argument is a recognized name. Currently, only `classic` " +
                "provides a TypeScript variant."

        private const val GitStrategy = "--git-strategy"
        private const val GitStrategyDescription =
            "Used when the template argument is a git repo. " +
                "Check https://docusaurus.io/docs/next/api/misc/create-docusaurus#git-strategy"

        private const val PackageManager = "--package-manager"
        private const val PackageManagerDescription =
            "Value should be one of npm, yarn, or pnpm. " +
                "Check https://docusaurus.io/docs/next/api/misc/create-docusaurus#package-manager"

        private const val SkipInstall = "--skip-install"
        private const val SkipInstallDescription =
            "f provided, Docusaurus will not automatically install dependencies after " +
                "creating the app. " +
                "Check https://docusaurus.io/docs/next/api/misc/create-docusaurus#skip-install"

        internal fun Project.registerDocusaurusCreateTask() {
            tasks.maybeRegisterLazily<DocusaurusCreateTask>(NAME) { task ->
                task.workingDir.set(layout.projectDirectory)
                task.name.set(docusaurusExtension.name)
                task.template.set("classic")

                task.npxCommand(
                    command = "create-docusaurus@latest",
                    additionalCommands =
                        listOf(
                            task.name,
                            task.template,
                            task.rootDir,
                        ),
                    arguments =
                        mapOf(
                            Typescript to task.typescript,
                            GitStrategy to task.gitStrategy,
                            PackageManager to task.packageManager,
                            SkipInstall to task.skipInstall,
                        ),
                )
            }
        }
    }
}
