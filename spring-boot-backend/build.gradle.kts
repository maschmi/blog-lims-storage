import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "2.0.0"
	kotlin("plugin.spring") version "2.0.0"
	kotlin("plugin.jpa") version "2.0.0"
}

group = "de.maschmi.blog"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies{
	val koTestVersion: String by properties
	val koTestSpringVersion: String by properties
	val mockkVersion: String by properties

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.h2database:h2")
//  implementation("org.liquibase:liquibase-core")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$koTestSpringVersion")
	testImplementation("io.mockk:mockk:${mockkVersion}")


	implementation("de.maschmi.blog:lims-storage-spring-boot-starter")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.add("-Xjsr305=strict")
		jvmTarget.set(JvmTarget.JVM_21)
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}
