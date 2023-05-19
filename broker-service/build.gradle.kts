plugins {
    id("kotlin-base")
    kotlin("plugin.allopen") version "1.7.10"
    id("io.quarkus") version "2.12.0.Final"
}

group = "org.broker"
version = "1.0.0-SNAPSHOT"

dependencies {
    // -- Application --
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:2.12.0.Final"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    api(project(":shared:domain"))

    // -- HTTP --
    implementation("io.quarkus:quarkus-resteasy-reactive")

    // Mediator
    api("com.trendyol:kediatr-quarkus-starter:1.0.2")

    // --- Testing ---
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

