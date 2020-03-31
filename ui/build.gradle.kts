
import Properties.BASE_API_URL
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("baselibplugin")
    `kotlin-android-extensions`
    id("androidx.navigation.safeargs.kotlin")
    id("io.fabric")
    id("com.google.firebase.firebase-perf")
    id("com.google.gms.google-services")
}

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
    androidExtensions.isExperimental = true
    setSourceSets()
}

dependencies {
    implementation(files("libs/MPChartLib.aar"))
    implementation(project(":data"))

    implementation(Dependencies.Libraries.retrofit) // FIXME: Make sure it is removed from here

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

    implementAppTesting()
    implementAppAndroidTesting()
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