pluginManagement {
    val springBootVersion: String by settings
    val springBootDependencyManagement: String by settings
    val kotlinVersion: String by settings
    repositories {
        gradlePluginPortal()
    }
    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springBootDependencyManagement
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
    }
}


rootProject.name = "spring-boot-backend"
