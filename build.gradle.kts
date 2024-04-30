plugins {
    kotlin("jvm") version "1.9.23"
}

group = "de.maschmi.blog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val koTestVersion = "5.8.0"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertion-core:$koTestVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}