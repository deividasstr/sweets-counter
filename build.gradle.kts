import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

buildscript {

    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")
    }

    dependencies {
        classpath(Dependencies.Plugins.androidGradle)
        classpath(Dependencies.Plugins.kotlinGradle)
        classpath(Dependencies.Plugins.navigationSafeArgs)
        classpath(Dependencies.Plugins.firebasePerf)
        classpath(Dependencies.Plugins.fabric)
        classpath(Dependencies.Plugins.googleServices)
        classpath(Dependencies.Plugins.sqlDelight)
    }
}

plugins {
    ktlint()
}

allprojects {
    setKtLint()

    repositories {
        google()
        jcenter()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/lion4ik/maven")
    }

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

fun Project.setKtLint() {
    apply(plugin = Dependencies.Plugins.ktLint)
    ktlint { debug.set(true) }
}