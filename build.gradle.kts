
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent*/

buildscript {

    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath(Dependencies.Libraries.buildGradle)
        classpath(Dependencies.Libraries.gradlePlugin)

        classpath(Dependencies.Libraries.objectboxGradlePlugin)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Dependencies.Versions.navigationVersion}")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${Dependencies.Versions.kotlinVersion}")
        classpath("com.google.firebase:perf-plugin:1.3.1")
        classpath("io.fabric.tools:gradle:1.31.1")
        classpath("com.google.gms:google-services:4.3.2")
    }
}

plugins {
    id("com.palantir.jacoco-full-report") version ("0.4.0")
    id("de.fayard.refreshVersions") version "0.7.0"
}

//http://engineering.rallyhealth.com/android/code-coverage/testing/2018/06/04/android-code-coverage.html
//apply from: rootProject.file("jacoco.gradle")

allprojects {
    //val ktlint = "com.github.shyiko:ktlint:0.29.0"

    repositories {
        google()
        jcenter()
        maven("http://objectbox.net/beta-repo/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/lion4ik/maven")
    }

    /*configurations {
        ktlint
    }*/

    /*dependencies {
        ktlint("com.github.shyiko:ktlint:0.29.0")
    }*/

    // https://github.com/google/dagger/issues/306
    /*afterEvaluate {
        tasks.withType(JavaCompile::class.java) {
            options.compilerArgs  = options.compilerArgs << "-Xmaxerrs" << "500"
        }
    }*/

    tasks.withType(Test::class.java) {
        testLogging {
            events = setOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED
                /*TestLogEvent.STANDARD_ERROR,
                TestLogEvent.STANDARD_OUT*/)
            exceptionFormat = TestExceptionFormat.FULL
            showCauses = true
            showExceptions = true
            showStackTraces = true
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}