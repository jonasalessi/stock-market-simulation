plugins {
    id("kotlin-base")
}


dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
