plugins {
    kotlin("jvm") version "2.4.21"
    id("com.gradleup.shadow") version "9.3.2"
    kotlin("kapt") version "2.4.21"
}

group = "de.itsphox"
version = "0.0.1"

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://eldonexus.de/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    implementation("org.bstats:bstats-bukkit:3.2.1")
    compileOnly("net.strokkur.commands:annotations-paper:2.3.1")
    kapt("net.strokkur.commands:processor-paper:2.3.1")
}

kotlin {
    jvmToolchain(25)
}

tasks.processResources {
    val properties = mapOf("version" to project.version)
    inputs.properties(properties)
    filesMatching("paper-plugin.yml") {
        expand(properties)
    }
}