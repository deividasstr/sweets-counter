
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
    plugins.apply("kotlin-android-extensions")
    plugins.apply("kotlin-kapt")
}

internal fun Project.configureDependencies() = dependencies {
    implementDomainModule()
    implementTesting()
}

fun Project.configureAndroid() {
    extensions.getByType<BaseExtension>().run {
        compileSdkVersion(Dependencies.Versions.androidTargetSdkVersion)
        defaultConfig {
            minSdkVersion(Dependencies.Versions.androidMinSdkVersion)
            targetSdkVersion(Dependencies.Versions.androidTargetSdkVersion)
            versionCode = Dependencies.Versions.versionCode
            versionName = Dependencies.Versions.versionName
            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
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