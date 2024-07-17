pluginManagement {
    val kotlinVersion: String by settings
    repositories {
        gradlePluginPortal()
    }
    plugins {
     kotlin("jvm") version kotlinVersion
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "lims-storage"

