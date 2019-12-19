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
        project.configureOther()
    }
}

private fun Project.configureOther() {
}

internal fun Project.configurePlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-kapt")
}

internal fun Project.configureDependencies() = dependencies {
    add("implementation", project(":domain"))
    //add("testImplementation", project(":domain", "testOutput"))
    implementKotlin()
    add("implementation", Dependencies.Libraries.threeTen)
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
        //implementation "androidx.core:core-ktx:1.0.2"
        /*testImplementation "junit:junit:4.12"
        androidTestImplementation "androidx.test.ext:junit:1.1.1"
        androidTestImplementation "androidx.test.espresso:espresso-core:3.2.0"*/
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

/*task ktlint (type: JavaExec, group: "verification") {
    description = "Check Kotlin code style."
    main = "com.github.shyiko.ktlint.Main"
    classpath = configurations.ktlint
    args "src/*
//*.kt"
}*/

/*check.dependsOn ktlint

    tasks.register("ktlintFormat", JavaExec::class.java) {
        description = "Fix Kotlin code style deviations."
        main = "com.github.shyiko.ktlint.Main"
        classpath = configurations.ktlint
        args = listOf("-F", "src/*
//*.kt")
    }
}*/