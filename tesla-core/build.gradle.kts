
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  `maven-publish`
  signing

  kotlin("multiplatform")
  kotlin("plugin.serialization")

  id("org.jetbrains.dokka")
}

val ktorVersion = "1.3.2"
val kotlinSerializationVersion = "0.20.0"
val kotlinCoroutinesVersion = "1.3.5"
val klockVersion = "1.7.3"

fun ktor(name: String): String = "io.ktor:ktor-$name:$ktorVersion"
fun kotlinx(name: String, version: String): String = "org.jetbrains.kotlinx:kotlinx-$name:$version"
fun klock(name: String): String = "com.soywiz.korlibs.klock:$name:$klockVersion"

val dokka by tasks.getting(DokkaTask::class) {
  outputDirectory = "$rootDir/docs/api"
  outputFormat = "gfm"
  multiplatform {}
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(kotlin("stdlib-common"))
        api(kotlinx("serialization-runtime-common", kotlinSerializationVersion))
        api(ktor("client-serialization"))
        api(ktor("client-cio"))
        api(klock("klock"))
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
  }

  jvm {
    mavenPublication {
      artifactId = "tesla-core-jvm"
    }

    compilations["main"].defaultSourceSet {
      dependencies {
        api(kotlin("stdlib-jdk8"))
        api(kotlinx("serialization-runtime", kotlinSerializationVersion))
        api(kotlinx("coroutines-core", kotlinCoroutinesVersion))
        api(ktor("client-serialization-jvm"))
        api(ktor("client-cio"))
      }
    }

    compilations["test"].defaultSourceSet {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-junit5"))
      }
    }
  }
}

val emptyJavadocJar by tasks.registering(Jar::class) {
  archiveClassifier.set("javadoc")
}

publishing {
  publications.withType<MavenPublication>().all {
    artifact(emptyJavadocJar.get())

    pom {
      name.set("Tesla Toolkit Core")
      description.set("Tesla API Client Library")
    }
  }
}
