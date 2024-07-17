import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
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
kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_21)
    }
    jvmToolchain(21)

}

tasks.test {
    useJUnitPlatform()
}