
import Dependencies.Libraries.androidCoroutines
import Dependencies.Libraries.coreCoroutines
import Dependencies.Libraries.retrofit
import Dependencies.Plugins.ktLint
import Dependencies.Versions.androidGradlePluginVersion
import Dependencies.Versions.androidxKtxVersion
import Dependencies.Versions.appCompatVersion
import Dependencies.Versions.constraintLayoutVersion
import Dependencies.Versions.coroutinesVersion
import Dependencies.Versions.daggerMockVersion
import Dependencies.Versions.daggerVersion
import Dependencies.Versions.espressoVersion
import Dependencies.Versions.fabricVersion
import Dependencies.Versions.firebasePerfVersion
import Dependencies.Versions.googleServicesVersion
import Dependencies.Versions.jUnitVersion
import Dependencies.Versions.kluentVersion
import Dependencies.Versions.kotlinVersion
import Dependencies.Versions.ktLintVersion
import Dependencies.Versions.leakCanaryVersion
import Dependencies.Versions.lifecycleVersion
import Dependencies.Versions.livedataTestVersion
import Dependencies.Versions.materialVersion
import Dependencies.Versions.mockitoAndroidVersion
import Dependencies.Versions.mockitoKotlinVersion
import Dependencies.Versions.morphNavBarVersion
import Dependencies.Versions.moshiVersion
import Dependencies.Versions.navigationVersion
import Dependencies.Versions.objectboxVersion
import Dependencies.Versions.okHttpVersion
import Dependencies.Versions.pagingVersion
import Dependencies.Versions.retrofitVersion
import Dependencies.Versions.runnerVersion
import Dependencies.Versions.simpleRecyclerViewVersion
import Dependencies.Versions.supportLibraryVersion
import Dependencies.Versions.threeTenAndroidVersion
import Dependencies.Versions.threeTenJavaVersion
import Dependencies.Versions.tickerVersion
import Dependencies.Versions.timberVersion
import Dependencies.Versions.workVersion
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependencySpec


object Dependencies {

    object Versions {

        //app
        const val versionCode = 5
        const val versionName = "1.0.3"

        //base gradle
        const val kotlinVersion = "1.3.70"
        const val objectboxVersion = "2.2.0"

        //Android
        const val androidMinSdkVersion = 21
        const val androidTargetSdkVersion = 29

        //Framework
        const val lifecycleVersion = "2.0.0"
        const val appCompatVersion = "1.0.2"
        const val supportLibraryVersion = "1.0.0"
        const val workVersion = "1.0.0-beta01"
        const val materialVersion = "1.0.0"
        const val navigationVersion = "2.2.0-rc02"
        const val pagingVersion = "2.1.0-rc01"

        //Firebase
        const val firebasePerfVersion = "1.3.1"
        const val fabricVersion = "1.31.1"
        const val googleServicesVersion = "4.3.3"

        //Other
        const val daggerVersion = "2.19"
        const val androidxKtxVersion = "1.2.0"
        const val okHttpVersion = "3.12.0"
        const val retrofitVersion = "2.5.1-SNAPSHOT"
        const val ktLintVersion = "9.2.1"
        const val moshiVersion = "1.6.0"

        //Debugging
        const val timberVersion = "4.7.1"
        const val leakCanaryVersion = "1.6.1"

        //Rx
        const val coroutinesVersion = "1.3.5"

        //Calendar
        const val threeTenAndroidVersion = "1.1.0"
        const val threeTenJavaVersion = "1.3.6"

        //UI
        const val tickerVersion = "2.0.0"
        const val morphNavBarVersion = "1.0.1"
        const val constraintLayoutVersion = "1.1.3"
        const val simpleRecyclerViewVersion = "2.0.5"

        //Testing
        const val daggerMockVersion = "0.8.4"
        const val jUnitVersion = "4.12"
        const val espressoVersion = "3.1.0-alpha3"
        const val testingSupportLibVersion = "0.1"
        const val mockitoKotlinVersion = "2.2.0"
        const val mockitoAndroidVersion = "3.3.3"
        const val runnerVersion = "1.1.0-alpha3"
        const val kluentVersion = "1.47"
        const val livedataTestVersion = "1.0.0"
        const val androidGradlePluginVersion = "3.6.1"
    }

    object Libraries {

        const val androidxKtx = "androidx.core:core-ktx:$androidxKtxVersion"
        const val threeTenJava = "org.threeten:threetenbp:$threeTenJavaVersion"
        const val threeTenAndroid =
            "com.jakewharton.threetenabp:threetenabp:$threeTenAndroidVersion"
        const val paging = "androidx.paging:paging-common:$lifecycleVersion"
        const val objectbox = "io.objectbox:objectbox-kotlin:$objectboxVersion"

        const val annotations = "androidx.annotation:annotation:$supportLibraryVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"

        const val coreCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val androidCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val testCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"

        const val junit = "junit:junit:$jUnitVersion"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion"
        const val mockitoAndroid = "org.mockito:mockito-inline:$mockitoAndroidVersion"
        const val kluent = "org.amshove.kluent:kluent:$kluentVersion"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object Plugins {
        const val androidGradle = "com.android.tools.build:gradle:$androidGradlePluginVersion"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val objectboxGradle = "io.objectbox:objectbox-gradle-plugin:$objectboxVersion"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
        const val firebasePerf = "com.google.firebase:perf-plugin:$firebasePerfVersion"
        const val fabric = "io.fabric.tools:gradle:$fabricVersion"
        const val googleServices = "com.google.gms:google-services:$googleServicesVersion"
        const val ktLint = "org.jlleitschuh.gradle.ktlint"
    }
}

fun DependencyHandler.implementNetworking() {
    add("implementation", retrofit)
    add("implementation", "com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    add("implementation", "com.squareup.moshi:moshi:$moshiVersion")
    add("implementation", "com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    add("implementation", "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    add("kapt", "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
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

fun DependencyHandler.implementObjectBoxTesting() {
    add("testImplementation", "io.objectbox:objectbox-linux:$objectboxVersion")
    add("testImplementation", "io.objectbox:objectbox-macos:$objectboxVersion")
    add("testImplementation", "io.objectbox:objectbox-windows:$objectboxVersion")
}

fun DependencyHandler.implementCoroutines() {
    add("implementation", coreCoroutines)
    add("implementation", androidCoroutines)
}

fun DependencyHandler.apiKotlin() {
    add("api", kotlin("stdlib"))
}

fun DependencyHandler.implementDagger() {
    add("implementation", "com.google.dagger:dagger:$daggerVersion")
    add("kapt", "com.google.dagger:dagger-compiler:$daggerVersion")
}

fun DependencyHandler.implementLeakCanary() {
    add("debugImplementation", "com.squareup.leakcanary:leakcanary-android:$leakCanaryVersion")
    add("releaseImplementation", "com.squareup.leakcanary:leakcanary-android-no-op:$leakCanaryVersion")
}

fun DependencyHandler.implementAndroidCoreLibs() {
    add("implementation", "androidx.appcompat:appcompat:$appCompatVersion")
    add("implementation", "com.google.android.material:material:$materialVersion")
    add("implementation", "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    add("implementation", Dependencies.Libraries.androidxKtx)
}

fun DependencyHandler.implementLifecycle() {
    add("implementation", "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    add("implementation", "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
}

fun DependencyHandler.implementWork() {
    add("implementation", "android.arch.work:work-runtime-ktx:$workVersion")
}

fun DependencyHandler.implementNavigation() {
    add("implementation", "androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    add("implementation", "androidx.navigation:navigation-ui-ktx:$navigationVersion")
}

fun DependencyHandler.implementFirebase() {
    add("implementation", "com.crashlytics.sdk.android:crashlytics:2.9.9")
    add("implementation", "com.google.firebase:firebase-perf:16.2.3")
}

fun DependencyHandler.implementPaging() {
    add("implementation", "androidx.paging:paging-runtime-ktx:$pagingVersion")
}

fun DependencyHandler.implementUiWidgets() {
    add("implementation", "androidx.dynamicanimation:dynamicanimation:$supportLibraryVersion")
    add("implementation", "com.tbuonomo:morph-bottom-navigation:$morphNavBarVersion")
    add("implementation", "com.robinhood.ticker:ticker:$tickerVersion")
    add("implementation", "com.jaychang:simplerecyclerview:$simpleRecyclerViewVersion")
    add("implementation", "com.jaychang:simplerecyclerview-kotlin-android-extensions:$simpleRecyclerViewVersion")
}

fun DependencyHandler.implementDaggerAndroid() {
    add("implementation", "com.google.dagger:dagger-android:$daggerVersion")
    add("implementation", "com.google.dagger:dagger-android-support:$daggerVersion")
    add("kapt", "com.google.dagger:dagger-android-processor:$daggerVersion")
    add("kaptAndroidTest", "com.google.dagger:dagger-compiler:$daggerVersion")
}

fun DependencyHandler.implementAppTesting() {
    add("testImplementation", Dependencies.Libraries.kluent)
    add("testImplementation", "com.squareup.leakcanary:leakcanary-android-no-op:$leakCanaryVersion")
    add("testImplementation", "androidx.paging:paging-common-ktx:$pagingVersion")
    add("testImplementation", "androidx.arch.core:core-testing:$lifecycleVersion")
    add("testImplementation", "com.jraska.livedata:testing-ktx:$livedataTestVersion")
}

fun DependencyHandler.implementAppAndroidTesting() {
    add("androidTestCompile", project(":testutils"))
    add("androidTestImplementation", "androidx.test.espresso:espresso-core:$espressoVersion")
    add("androidTestImplementation", "androidx.test.espresso:espresso-contrib:$espressoVersion")
    add("androidTestImplementation", "android.arch.work:work-testing:$workVersion")
    add("androidTestImplementation", "androidx.test:runner:$runnerVersion")
    add("androidTestImplementation", "androidx.test:rules:$runnerVersion")
    add("androidTestImplementation", Dependencies.Libraries.mockitoAndroid)
    add("androidTestImplementation", Dependencies.Libraries.mockitoKotlin)
    add("androidTestImplementation", "com.squareup.okhttp3:mockwebserver:$okHttpVersion")
    add("androidTestImplementation", "androidx.arch.core:core-testing:$lifecycleVersion")
    add("androidTestImplementation", "com.google.dagger:dagger:$daggerVersion")
    add("androidTestImplementation", "com.github.fabioCollini.daggermock:daggermock-kotlin:$daggerMockVersion")
}

fun DependencyHandler.implementCommonTestUtils() {
    add("testCompile", project(":testutils"))
}

fun DependencyHandler.implementDomainModule() {
    add("implementation", project(":domain"))
}

fun org.gradle.plugin.use.PluginDependenciesSpec.ktlint(): PluginDependencySpec {
    return id (ktLint) version ktLintVersion
}

