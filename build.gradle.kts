plugins {
    kotlin("jvm") version "2.2.10"
    application
}

group = "com.tbawor"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.tbawor.MainKt")
}
