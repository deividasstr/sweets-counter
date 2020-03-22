
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
        classpath(Dependencies.Plugins.androidGradle)
        classpath(Dependencies.Plugins.kotlinGradle)
        classpath(Dependencies.Plugins.objectboxGradle)
        classpath(Dependencies.Plugins.navigationSafeArgs)
        classpath(Dependencies.Plugins.firebasePerf)
        classpath(Dependencies.Plugins.fabric)
        classpath(Dependencies.Plugins.googleServices)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("http://objectbox.net/beta-repo/")
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