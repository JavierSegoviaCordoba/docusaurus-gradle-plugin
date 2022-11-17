package com.javiersc.docusaurus.gradle.plugin

import javax.inject.Inject
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

public abstract class DocusaurusExtension
@Inject
constructor(
    objects: ObjectFactory,
    layout: ProjectLayout,
) {

    public val directory: DirectoryProperty =
        objects.directoryProperty().convention(layout.projectDirectory.dir(".docs"))
}

public abstract class DocusaurusConfigExtension
constructor(
    objects: ObjectFactory,
    layout: ProjectLayout,
) {
    public val title: Property<String> = objects.property()
    public val url: Property<String> = objects.property<String>().convention("/")
    public val baseUrl: Property<String> = objects.property()
}
