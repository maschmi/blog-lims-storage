rootProject.name = "blog-lims"

includeBuild("lims-storage") {
    dependencySubstitution {
        substitute(module("de.maschmi.blog:lims-storage")).using(project(":"))
    }
}
includeBuild("spring-boot-backend")
