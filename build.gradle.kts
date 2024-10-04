import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask;

plugins {
    id("java")
    id("dev.s7a.gradle.minecraft.server") version "2.1.1"
}

group = "dev.cabotmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
task<LaunchMinecraftServerTask>("runServer") {
    outputs.upToDateWhen { false }
    dependsOn("build")

    doFirst {
        copy {
            from(layout.buildDirectory.file("libs/CabotPlugin-1.0-SNAPSHOT.jar"))
            into(layout.buildDirectory.file("MinecraftServer/plugins"))
        }
    }

    jarUrl.set(LaunchMinecraftServerTask.JarUrl.Paper("1.21.1"))
    agreeEula.set(true)
}