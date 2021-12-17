import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.0"
}

repositories {
    jcenter()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}