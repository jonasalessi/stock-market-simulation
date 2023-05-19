rootProject.name = "stock-market"

pluginManagement {
    includeBuild("build-plugins/kotlin-base")
}

include("broker-service")
include("shared")
include("shared:domain")