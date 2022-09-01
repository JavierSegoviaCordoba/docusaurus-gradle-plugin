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

    public val name: Property<String> = objects.property<String>().convention("website")

    public val port: Property<String> = objects.property<String>().convention("3000")

    public val dependsOnKillPortTask: Property<Boolean> =
        objects.property<Boolean>().convention(true)

    public val directory: DirectoryProperty =
        objects.directoryProperty().convention(layout.projectDirectory.dir(name))
}
