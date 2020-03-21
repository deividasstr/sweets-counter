import Dependencies.Versions.timberVersion
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType

class BaseLibPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins()
        project.configureAndroid()
        project.configureDependencies()
    }
}

internal fun Project.configurePlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-kapt")
}

internal fun Project.configureDependencies() = dependencies {
    add("implementation", project(":domain"))
    implementKotlin()
    add("implementation", Dependencies.Libraries.threeTenAndroid)
    add("implementation", "com.jakewharton.timber:timber:$timberVersion")

    implementCoroutines()
    implementTesting()
}

fun Project.configureAndroid() {
    extensions.getByType<BaseExtension>().run {
        compileSdkVersion(29)
        defaultConfig {
            minSdkVersion(21)
            targetSdkVersion(29)
            versionCode = 5
            versionName = "1.0.3"
            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }

            getByName("debug") {
                isTestCoverageEnabled = true
            }
        }

        packagingOptions {
            exclude("META-INF/NOTICE.txt")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        sourceSets["main"].java.srcDir("src/main/kotlin")
        sourceSets["test"].java.srcDir("src/test/kotlin")
        sourceSets["androidTest"].java.srcDir("src/androidTest/kotlin")

        testOptions {
            unitTests.isReturnDefaultValues = true
        }
    }
}