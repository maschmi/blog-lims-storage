plugins {
    kotlin("jvm") version "1.9.23"
}

group = "de.maschmi.blog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val koTestVersion by properties
val mockkVersion by properties

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")
    testImplementation("io.mockk:mockk:${mockkVersion}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}