rootProject.name = "stock-market"
pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
        mavenLocal()
    }
   includeBuild("/build-plugins")
}
include("shared:domain")
include("broker")
