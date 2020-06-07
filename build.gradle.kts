import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.72"
    `maven-publish`
}
group = "nonstdlib"
version = "0.1"

repositories {
	mavenCentral()
	maven { setUrl("https://dl.bintray.com/dkandalov/maven") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
	testImplementation("junit:junit:4.12")
	testImplementation("datsok:datsok:0.1")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}