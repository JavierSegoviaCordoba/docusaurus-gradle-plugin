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

public abstract class DocusaurusSwizzleTask : YarnTask() {

    @get:Input
    @get:Option(option = "themeName", description = ThemeDescription)
    public val themeName: Property<String> = objects.property()

    @get:Input
    @get:Option(option = "componentName", description = ComponentDescription)
    public val componentName: Property<String> = objects.property()

    @get:Input
    @get:Optional
    @get:Option(option = "list", description = ListDescription)
    public val list: Property<Boolean?> = objects.property()

    @get:Input
    @get:Optional
    @get:Option(option = "eject", description = EjectDescription)
    public val eject: Property<Boolean?> = objects.property()

    @get:Input
    @get:Optional
    @get:Option(option = "wrap", description = WrapDescription)
    public val wrap: Property<Boolean?> = objects.property()

    @get:Input
    @get:Optional
    @get:Option(option = "danger", description = DangerDescription)
    public val danger: Property<Boolean?> = objects.property()

    @get:Input
    @get:Optional
    @get:Option(option = "typescript", description = TypescriptDescription)
    public val typescript: Property<Boolean?> = objects.property()

    @get:Input
    @get:Optional
    @get:Option(option = "config", description = ConfigDescription)
    public val config: Property<String?> = objects.property()

    init {
        group = "documentation"
        dependsOn(DocusaurusCheckPackageJsonTask.NAME)
        dependsOn(YarnInstallTask.NAME)
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

        internal fun Project.registerDocusaurusSwizzleTask() {
            tasks.maybeRegisterLazily<DocusaurusSwizzleTask>(NAME) { task ->
                task.workingDir.set(file(docusaurusExtension.directory))

                task.yarnCommand(
                    preCommand = "run",
                    command = "swizzle",
                    additionalCommands =
                        listOf(
                            task.themeName,
                            task.componentName,
                        ),
                    arguments =
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
