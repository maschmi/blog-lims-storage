import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
//	kotlin("kapt")
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
	val mapstructVersion: String by properties

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.h2database:h2")
    implementation("org.liquibase:liquibase-core")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
	testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$koTestSpringVersion")
	testImplementation("io.mockk:mockk:${mockkVersion}")


	implementation("de.maschmi.blog:lims-storage-boot-starter")

	implementation("org.mapstruct:mapstruct:$mapstructVersion")

	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.add("-Xjsr305=strict")
		jvmTarget.set(JvmTarget.JVM_21)
	}
	jvmToolchain(21)
}

tasks.withType<JavaCompile> {
	options.compilerArgs.apply {
		add("-Amapstruct.defaultComponentModel=spring")
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}
