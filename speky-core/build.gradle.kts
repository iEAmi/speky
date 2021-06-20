plugins {
    kotlin("jvm") version "1.5.10"
    `java-library`
    idea
    eclipse
}

group = "com.github.speky"
version = "0.1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
