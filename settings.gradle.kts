import com.javiersc.gradle.properties.extensions.getBooleanProperty

pluginManagement {
    val hubdleVersion: String =
        file("$rootDir/gradle/libs.versions.toml")
            .readLines()
            .first { it.contains("hubdle") }
            .split("\"")[1]

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    plugins {
        id("com.javiersc.hubdle") version hubdleVersion
    }
}

plugins {
    id("com.javiersc.hubdle")
}

hubdleSettings {
    autoInclude {
        val isSandboxEnabled = getBooleanProperty("sandbox.enabled").orNull ?: false
        if (!isSandboxEnabled) excludedBuilds("sandbox")
    }
}
