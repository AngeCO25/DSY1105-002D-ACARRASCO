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

// Java y Kotlin deben compilar para la misma version de destino,
// si no coinciden Gradle detiene la compilacion
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

application {
    // Clase que se ejecuta al correr el programa (viene del archivo Main.kt)
    mainClass.set("MainKt")
}