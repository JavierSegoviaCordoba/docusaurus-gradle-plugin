@file:Suppress("LeakingThis")

package com.javiersc.docusaurus.gradle.plugin.tasks

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.npm.task.NpmTask
import com.javiersc.docusaurus.gradle.plugin.DocusaurusExtension
import com.javiersc.docusaurus.gradle.plugin.internal.cliLink
import com.javiersc.docusaurus.gradle.plugin.internal.npmCommand
import com.javiersc.docusaurus.gradle.plugin.internal.nullConvention
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.options.Option

/**
 * Builds and serves a preview of your site locally
 *
 * [Docusaurus CLI start command](https://docusaurus.io/docs/cli#docusaurus-start-sitedir)
 */
public abstract class DocusaurusStartTask : NpmTask() {

    @Input
    @Optional
    @Option(option = "port", description = PortDescription)
    public val port: Property<String?> = nullConvention()

    @Input
    @Optional
    @Option(option = "host", description = HostDescription)
    public val host: Property<String?> = nullConvention()

    @Input
    @Optional
    @Option(option = "hot-only", description = HotOnlyDescription)
    public val hotOnly: Property<Boolean?> = nullConvention()

    @Input
    @Optional
    @Option(option = "no-open", description = NoOpenDescription)
    public val noOpen: Property<Boolean?> = nullConvention()

    @Input
    @Optional
    @Option(option = "config", description = ConfigDescription)
    public val config: Property<String?> = nullConvention()

    @Input
    @Optional
    @Option(option = "poll", description = PollDescription)
    public val poll: Property<Boolean?> = nullConvention()

    @Input
    @Optional
    @Option(option = "no-minify", description = NoMinifyDescription)
    public val noMinify: Property<Boolean?> = nullConvention()

    init {
        group = "documentation"
        description =
            "Builds and serves a preview of your site locally.".cliLink("docusaurus-start-sitedir")
    }

    public companion object {
        public const val NAME: String = "docusaurusStart"

        private const val Port = "--port"
        private const val PortDescription = "Specifies the port of the dev server."

        private const val Host = "--host"
        private const val HostDescription = "Specify a host to use."

        private const val HotOnly = "--hot-only"
        private const val HotOnlyDescription =
            "Enables Hot Module Replacement without page refresh as a fallback in case of build failures."

        private const val NoOpen = "--no-open"
        private const val NoOpenDescription = "Do not open automatically the page in the browser."

        private const val Config = "--config"
        private const val ConfigDescription =
            "Path to Docusaurus config file, default to `[siteDir]/docusaurus.config.js`"

        private const val Poll = "--poll"
        private const val PollDescription =
            "Use polling of files rather than watching for live reload as a fallback in " +
                "environments where watching doesn't work."

        private const val NoMinify = "--no-minify"
        private const val NoMinifyDescription = "Build website without minimizing"

        internal fun Project.registerDocusaurusStartTask(docusaurusExtension: DocusaurusExtension) {
            tasks.maybeRegisterLazily<DocusaurusStartTask>(NAME) { task ->
                task.dependsOn(DocusaurusCheckPackageJsonTask.NAME)
                task.dependsOn(NpmInstallTask.NAME)

                task.workingDir.set(file(docusaurusExtension.directory))

                task.npmCommand(
                    command = "start",
                    options =
                        mapOf(
                            Port to task.port,
                            Host to task.host,
                            HotOnly to task.hotOnly,
                            NoOpen to task.noOpen,
                            Config to task.config,
                            Poll to task.poll,
                            NoMinify to task.noMinify,
                        )
                )
            }
        }
    }
}
