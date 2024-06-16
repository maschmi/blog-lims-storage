plugins {
    kotlin("kapt")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    kapt("org.springframework.boot:spring-boot-autoconfigure-processor")

    api("de.maschmi.blog:lims-storage")
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory = file("$projectDir/generated")
}