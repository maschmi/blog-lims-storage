rootProject.name = "blog-lims"

includeBuild("lims-storage") {
    dependencySubstitution {
        substitute(module("de.maschmi.blog:lims-storage")).using(project(":"))
    }
}
includeBuild("lims-storage-boot") {
    dependencySubstitution {
        substitute(module("de.maschmi.blog:lims-storage-spring-boot-starter")).using(project(":lims-storage-spring-boot-starter"))
    }
}

includeBuild("spring-boot-backend")
