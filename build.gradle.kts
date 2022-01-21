plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ru.itapelsin"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    val mockitoVersion = "4.2.0"
    val sqliteJdbcVersion = "3.36.0.3"
    val poiVersion = "5.2.0"
    val junitJupiterApiVersion = "5.8.2"
    implementation("org.xerial:sqlite-jdbc:${sqliteJdbcVersion}")
    implementation("org.apache.poi:poi:${poiVersion}")
    implementation("org.apache.poi:poi-ooxml:${poiVersion}")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterApiVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Jar>() {
    manifest {
        attributes("Main-Class" to "ru.itapelsin.mei.Main")
    }
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    destinationDirectory.set(file("out"))
    archiveFileName.set("app.jar")
}