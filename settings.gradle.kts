rootProject.name = "stock-market"

pluginManagement {
    includeBuild("/build-plugins/kotlin-base")
}

include("broker-service")
include("broker-service:domain")
include("broker-service:application")
include("broker-service:infra")

include("shared")
include("shared:domain")