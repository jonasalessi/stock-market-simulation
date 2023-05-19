plugins {
    id("kotlin-base")
    kotlin("plugin.allopen") version "1.8.10"
    id("io.quarkus") version "3.0.3.Final"
}
group = "org.broker"
version = "1.0.0-SNAPSHOT"

dependencies {
    // -- Application --
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.0.3.Final"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    api(project(":shared:domain"))

    // -- HTTP --
    implementation("io.quarkus:quarkus-resteasy-reactive")

    // Mediator
    implementation("com.trendyol:kediatr-core:2.1.0")
    implementation("com.trendyol:kediatr-quarkus-starter:2.1.0")

    // --- Testing ---
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}
repositories {
    mavenCentral()
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}
tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}