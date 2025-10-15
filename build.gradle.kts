plugins {
    kotlin("jvm") version "2.2.10"
    application
}

group = "com.tbawor"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val gdxVersion = "1.13.5"

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    runtimeOnly("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")

    // FreeType support for loading TTF/OTF fonts
    implementation("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
    runtimeOnly("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.tbawor.DesktopLauncher")
}
