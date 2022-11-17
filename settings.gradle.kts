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
        id("com.javiersc.hubdle.settings") version hubdleVersion
    }
}

plugins {
    id("com.javiersc.hubdle.settings")
}

hubdleSettings {
    autoInclude {
        val isSandboxEnabled = getBooleanProperty("sandbox.enabled")
        if (!isSandboxEnabled) excludedBuilds("sandbox")
    }
}
