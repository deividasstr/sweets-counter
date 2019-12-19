import Dependencies.Versions.retrofitVersion
import Properties.BASE_API_URL
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("baselibplugin")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("io.fabric")
    id("com.google.firebase.firebase-perf")
    id("kotlin-allopen")
    id("com.google.gms.google-services")
}

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.3.50")
    }
}

allOpen {
    annotation("com.deividasstr.data.utils.OpenClass")
}

//enableJacoco(project, "Debug")

android {
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    if (keystorePropertiesFile.exists()) { // Does not exist in REPO
        val keystoreProperties = Properties()
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        createSigningConfigs(keystoreProperties)
        setSigningConfigToRelease()
    }

    defaultConfig {
        buildConfigField("String", "BASE_API_URL", GradleUtils.getString(BASE_API_URL))
        applicationId = "com.deividasstr.sweetscounter"
        testInstrumentationRunner = "com.deividasstr.ui.utils.TestAppRunner"
    }

    dataBinding.isEnabled = true

    testOptions {
        animationsDisabled = true
        unitTests.isIncludeAndroidResources = true
    }

    setSourceSets()

    androidExtensions.isExperimental = true
}

dependencies {
    implementation(project(":data"))
    //implementation(project(":MPChartLib"))

    implementation ("androidx.core:core-ktx:1.1.0")
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementAndroidCoreLibs()
    implementUiWidgets()

    implementLifecycle()
    implementNavigation()
    implementWork()
    implementPaging()

    implementDagger()
    implementDaggerAndroid()

    implementFirebase()
    implementLeakCanary()

    implementObjectBoxTesting()
    implementAppTesting()
    implementAppAndroidTesting()

    //androidTestImplementation(project(":domain", "testOutput"))
}

fun BaseAppModuleExtension.createSigningConfigs(keystoreProperties: Properties) {
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
}

fun BaseAppModuleExtension.setSigningConfigToRelease() {
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

fun BaseAppModuleExtension.setSourceSets() {
    sourceSets {
        val sharedTestDir = "src/sharedTest/kotlin"
        val sharedTestResDir = "src/sharedTest/res"
        getByName("test").java.srcDir(sharedTestDir)
        getByName("androidTest").java.srcDir(sharedTestDir)
        getByName("androidTest").res.srcDir(sharedTestResDir)
    }
}