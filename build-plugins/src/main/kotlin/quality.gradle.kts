plugins {
    id("kotlin-base")
}


dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
