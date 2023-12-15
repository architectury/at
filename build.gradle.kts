plugins {
    `java-library`
    `maven-publish`
    id("net.kyori.indra.licenser.spotless") version "3.1.3"
}

val baseVersion: String by project
version = baseVersion + "." + (System.getenv("GITHUB_RUN_NUMBER") ?: "9999")

logger.lifecycle(":building at v${version}")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.cadixdev:bombe:0.3.4")
    api("net.fabricmc:mapping-io:0.4.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<Jar>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}

indraSpotlessLicenser {
    licenseHeaderFile("LICENSE")
    newLine(true)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
