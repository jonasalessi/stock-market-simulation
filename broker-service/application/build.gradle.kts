plugins {
    id("quality")
}

dependencies {
    implementation(project(":broker-service:domain"))
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:2.0.2")

    // Mediator
    api("com.trendyol:kediatr-quarkus-starter:1.0.2")
}

group = "org.broker.application"
version = "1.0.0-SNAPSHOT"