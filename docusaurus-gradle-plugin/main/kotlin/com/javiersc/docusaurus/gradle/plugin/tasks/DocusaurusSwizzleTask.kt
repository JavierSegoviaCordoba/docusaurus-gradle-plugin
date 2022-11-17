@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.npm.task.NpmTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.npmCommand
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusSwizzleTask : NpmTask() {

    @Input
    @Option(option = "themeName", description = ThemeDescription)
    public val themeName: Property<String> = objects.property()

    @Input
    @Option(option = "componentName", description = ComponentDescription)
    public val componentName: Property<String> = objects.property()

    @Input
    @Optional
    @Option(option = "list", description = ListDescription)
    public val list: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "eject", description = EjectDescription)
    public val eject: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "wrap", description = WrapDescription)
    public val wrap: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "danger", description = DangerDescription)
    public val danger: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "typescript", description = TypescriptDescription)
    public val typescript: Property<String?> = objects.property()

    @Input
    @Optional
    @Option(option = "config", description = ConfigDescription)
    public val config: Property<String?> = objects.property()

    init {
        group = "documentation"
    }

    public companion object {
        public const val NAME: String = "docusaurusSwizzle"

        private const val ThemeDescription = "The name of the theme to swizzle from."

        private const val ComponentDescription = "The name of the theme component to swizzle."

        private const val List = "--list"
        private const val ListDescription = "Display components available for swizzling."

        private const val Eject = "--eject"
        private const val EjectDescription = "Eject the theme component."

        private const val Wrap = "--wrap"
        private const val WrapDescription = "Wrap the theme component."

        private const val Danger = "--danger"
        private const val DangerDescription = "Allow immediate swizzling of unsafe components."

        private const val Typescript = "--typescript"
        private const val TypescriptDescription = "Swizzle the TypeScript variant component."

        private const val Config = "--config"
        private const val ConfigDescription =
            "Path to Docusaurus config file, default to `[siteDir]/docusaurus.config.js`"

        internal fun Project.registerDocusaurusSwizzleTask(
            docusaurusExtension: DocusaurusExtension
        ) {
            tasks.maybeRegisterLazily<DocusaurusSwizzleTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.npmCommand(
                    command = "build",
                    values =
                        listOf(
                            task.themeName,
                            task.componentName,
                        ),
                    options =
                        mapOf(
                            List to task.list,
                            Eject to task.eject,
                            Wrap to task.wrap,
                            Danger to task.danger,
                            Typescript to task.typescript,
                            Config to task.config,
                        )
                )
            }
        }
    }
}
