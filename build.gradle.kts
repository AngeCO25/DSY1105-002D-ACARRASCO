plugins {
    kotlin("jvm") version "2.1.10"
    application
}

group = "cl.lavexpress"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Libreria de corrutinas: permite que las esperas del sensor no bloqueen el programa
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

application {
    mainClass.set("MainKt")
}
