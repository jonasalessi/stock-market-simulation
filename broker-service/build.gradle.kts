
plugins {
    id("kotlin-base")
}

allprojects{
    apply {
        plugin("kotlin-base")
    }

    group = "org.broker"
    version = "1.0.0-SNAPSHOT"

    dependencies {
        api(project(":shared:domain"))
    }
}
