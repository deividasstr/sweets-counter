
import Dependencies.Libraries.androidCoroutines
import Dependencies.Libraries.coreCoroutines
import Dependencies.Libraries.retrofit
import Dependencies.Plugins.ktLint
import Dependencies.Versions.androidGradlePluginVersion
import Dependencies.Versions.fabricVersion
import Dependencies.Versions.firebasePerfVersion
import Dependencies.Versions.googleServicesVersion
import Dependencies.Versions.kotlinVersion
import Dependencies.Versions.ktLintVersion
import Dependencies.Versions.navigationVersion
import Dependencies.Versions.sqlDelightVersion
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependencySpec

object Dependencies {

    object Versions {

        //app
        const val versionCode = 5
        const val versionName = "1.0.3"

        //Android
        const val androidMinSdkVersion = 21
        const val androidTargetSdkVersion = 29
        const val navigationVersion = "2.2.0-rc02"

        //Plugins
        const val kotlinVersion = "1.3.71"
        const val firebasePerfVersion = "1.3.1"
        const val fabricVersion = "1.31.2"
        const val googleServicesVersion = "4.3.3"
        const val androidGradlePluginVersion = "3.6.1"
        const val ktLintVersion = "9.2.1"
        const val sqlDelightVersion = "1.2.2"
    }

    object Libraries {
        const val sqlDelight = "com.squareup.sqldelight:android-driver:_"
        const val sqlDelightPaging = "com.squareup.sqldelight:android-paging-extensions:_"
        const val androidxKtx = "androidx.core:core-ktx:_"
        const val threeTenJava = "org.threeten:threetenbp:_"
        const val threeTenAndroid = "com.jakewharton.threetenabp:threetenabp:_"
        const val paging = "androidx.paging:paging-common:_"
        const val annotations = "androidx.annotation:annotation:_"
        const val retrofit = "com.squareup.retrofit2:retrofit:_"
        const val coreCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:_"
        const val androidCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:_"
        const val testCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:_"
        const val junit = "junit:junit:_"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:_"
        const val mockitoAndroid = "org.mockito:mockito-inline:_"
        const val kluent = "org.amshove.kluent:kluent:_"
        const val timber = "com.jakewharton.timber:timber:_"
        const val sqlDelightJvm = "com.squareup.sqldelight:sqlite-driver:_"
    }

    object Plugins {
        const val androidGradle = "com.android.tools.build:gradle:$androidGradlePluginVersion"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
        const val firebasePerf = "com.google.firebase:perf-plugin:$firebasePerfVersion"
        const val fabric = "io.fabric.tools:gradle:$fabricVersion"
        const val googleServices = "com.google.gms:google-services:$googleServicesVersion"
        const val ktLint = "org.jlleitschuh.gradle.ktlint"
        const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion"
    }
}

fun DependencyHandler.implementNetworking() {
    add("implementation", retrofit)
    add("implementation", "com.squareup.retrofit2:converter-moshi:_")
    add("implementation", "com.squareup.moshi:moshi:_")
    add("implementation", "com.squareup.okhttp3:logging-interceptor:_")
    add("implementation", "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    add("kapt", "com.squareup.moshi:moshi-kotlin-codegen:_")
}

fun DependencyHandler.implementTesting() {
    implementCommonTestUtils()
    add("testImplementation", Dependencies.Libraries.kluent)
    add("testImplementation", Dependencies.Libraries.junit)
    add("testImplementation", Dependencies.Libraries.mockitoKotlin)
    add("testImplementation", Dependencies.Libraries.mockitoAndroid)
    add("testImplementation", Dependencies.Libraries.threeTenJava)
    add("testImplementation", Dependencies.Libraries.testCoroutines)
}

fun DependencyHandler.implementCoroutines() {
    add("implementation", coreCoroutines)
    add("implementation", androidCoroutines)
}

fun DependencyHandler.apiKotlin() {
    add("api", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
}

fun DependencyHandler.implementDagger() {
    add("implementation", "com.google.dagger:dagger:_")
    add("kapt", "com.google.dagger:dagger-compiler:_")
}

fun DependencyHandler.implementLeakCanary() {
    add("debugImplementation", "com.squareup.leakcanary:leakcanary-android:_")
}

fun DependencyHandler.implementAndroidCoreLibs() {
    add("implementation", "androidx.appcompat:appcompat:_")
    add("implementation", "com.google.android.material:material:_")
    add("implementation", "androidx.constraintlayout:constraintlayout:_")
    add("implementation", Dependencies.Libraries.androidxKtx)
}

fun DependencyHandler.implementLifecycle() {
    add("implementation", "androidx.lifecycle:lifecycle-extensions:_")
    add("implementation", "androidx.lifecycle:lifecycle-common-java8:_")
}

fun DependencyHandler.implementWork() {
    add("implementation", "android.arch.work:work-runtime-ktx:_")
}

fun DependencyHandler.implementNavigation() {
    add("implementation", "androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    add("implementation", "androidx.navigation:navigation-ui-ktx:$navigationVersion")
}

fun DependencyHandler.implementFirebase() {
    add("implementation", "com.crashlytics.sdk.android:crashlytics:_")
    add("implementation", "com.google.firebase:firebase-perf:_")
}

fun DependencyHandler.implementPaging() {
    add("implementation", "androidx.paging:paging-runtime-ktx:_")
}

fun DependencyHandler.implementUiWidgets() {
    add("implementation", "androidx.dynamicanimation:dynamicanimation:_")
    add("implementation", "com.tbuonomo:morph-bottom-navigation:_")
    add("implementation", "com.robinhood.ticker:ticker:_")
    add("implementation", "com.jaychang:simplerecyclerview:_")
    add("implementation", "com.jaychang:simplerecyclerview-kotlin-android-extensions:_")
}

fun DependencyHandler.implementDaggerAndroid() {
    add("implementation", "com.google.dagger:dagger-android:_")
    add("implementation", "com.google.dagger:dagger-android-support:_")
    add("kapt", "com.google.dagger:dagger-android-processor:_")
    add("kaptAndroidTest", "com.google.dagger:dagger-compiler:_")
}

fun DependencyHandler.implementAppTesting() {
    add("testImplementation", Dependencies.Libraries.kluent)
    add("testImplementation", "androidx.paging:paging-common-ktx:_")
    add("testImplementation", "androidx.arch.core:core-testing:_")
    add("testImplementation", "com.jraska.livedata:testing-ktx:_")
}

fun DependencyHandler.implementAppAndroidTesting() {
    add("androidTestCompile", project(":testutils"))
    add("androidTestImplementation", "androidx.test.espresso:espresso-core:_")
    add("androidTestImplementation", "androidx.test.espresso:espresso-contrib:_")
    add("androidTestImplementation", "android.arch.work:work-testing:_")
    add("androidTestImplementation", "androidx.test:runner:_")
    add("androidTestImplementation", "androidx.test:rules:_")
    add("androidTestImplementation", Dependencies.Libraries.mockitoAndroid)
    add("androidTestImplementation", Dependencies.Libraries.mockitoKotlin)
    add("androidTestImplementation", "com.squareup.okhttp3:mockwebserver:_")
    add("androidTestImplementation", "androidx.arch.core:core-testing:_")
    add("androidTestImplementation", "com.google.dagger:dagger:_")
    add("androidTestImplementation", "com.github.fabioCollini.daggermock:daggermock-kotlin:_")
}

fun DependencyHandler.implementCommonTestUtils() {
    add("testCompile", project(":testutils"))
}

fun DependencyHandler.implementDomainModule() {
    add("implementation", project(":domain"))
}

fun org.gradle.plugin.use.PluginDependenciesSpec.ktlint(): PluginDependencySpec {
    return id(ktLint) version ktLintVersion
}
