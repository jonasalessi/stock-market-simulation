plugins {
    kotlin("plugin.allopen") version "1.7.10"
    id("io.quarkus") version "2.12.0.Final"
}

dependencies {
    // -- Application --
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:2.12.0.Final"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    // -- HTTP --
    implementation("io.quarkus:quarkus-resteasy-reactive")

    // -- Order --
    implementation(project(":broker-service:application"))

    // --- Testing ---
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

