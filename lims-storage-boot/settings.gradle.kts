pluginManagement {
    val springBootVersion: String by settings
    val springBootDependencyManagement: String by settings
    val kotlinVersion: String by settings
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("io.spring.dependency-management") version springBootDependencyManagement
        kotlin("kapt") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}

include(":lims-storage-spring-boot")
include(":lims-storage-spring-boot-starter")

rootProject.name = "lims-storage-boot"
