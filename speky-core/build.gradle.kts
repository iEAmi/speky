plugins {
  kotlin("jvm") version "1.5.10"
  id("io.gitlab.arturbosch.detekt").version("1.17.1")
  `java-library`
  idea
  eclipse
  jacoco
}

group = "com.github.speky"
version = "0.1.0"

repositories {
  mavenLocal()
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))

  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")

  testImplementation("io.kotest:kotest-runner-junit5:4.6.0")
  testImplementation("io.kotest:kotest-assertions-core:4.6.0")
  testImplementation("io.kotest:kotest-property:4.6.0")
}

tasks.test {
  useJUnitPlatform()
}


tasks.jacocoTestReport {
  reports {
    xml.isEnabled = true
  }
}

tasks.register("buildCoverage") {
  dependsOn("build")
  doLast {
    tasks.jacocoTestReport.get().generate()
  }
}

detekt {
  toolVersion = "1.17.1"
  config = files("../detekt.yml")
  buildUponDefaultConfig = true
}
