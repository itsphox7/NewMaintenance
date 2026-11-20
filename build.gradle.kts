plugins {
    kotlin("jvm") version "2.4.21"
    id("com.gradleup.shadow") version "9.3.2"
    kotlin("kapt") version "2.4.21"
    id("com.modrinth.minotaur") version "2.+"
}

group = "de.itsphox"
version = "1.0.0"

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://eldonexus.de/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")
    implementation("org.bstats:bstats-bukkit:3.2.1")
    compileOnly("net.strokkur.commands:annotations-paper:2.3.1")
    kapt("net.strokkur.commands:processor-paper:2.3.1")
    implementation("com.github.QuadrixYT18.Qavlen:qavlen-config:v0.5.3")
    implementation("com.github.QuadrixYT18.Qavlen:qavlen-updater:v0.5.3")
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

tasks.shadowJar {
    archiveClassifier.set("")
    configurations = project.configurations.runtimeClasspath.map { setOf(it) }
    relocate("de.quadrixyt.qavlen", "de.itsphox.newmaintenance.libs.qavlen")
    relocate("org.bstats", "de.itsphox.newmaintenance.libs.bstats")
    mergeServiceFiles()
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("aXq1wdra")
    versionNumber.set("${project.version}")
    versionType.set("release")
    uploadFile.set(tasks.named("shadowJar"))
    gameVersions.addAll("26.3")
    loaders.add("paper")
    syncBodyFrom.set(rootProject.file("README.md").readText())
}