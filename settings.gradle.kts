rootProject.name = "blog-lims"

includeBuild("lims-storage") {
    dependencySubstitution {
        substitute(module("de.maschmi.blog:lims-storage")).using(project(":"))
    }
}
includeBuild("lims-storage-boot") {
    dependencySubstitution {
        substitute(module("de.maschmi.blog:lims-storage-boot-starter")).using(project(":lims-storage-boot-starter"))
    }
}

includeBuild("spring-boot-backend")
