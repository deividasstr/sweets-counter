
import Dependencies.Libraries.androidCoroutines
import Dependencies.Libraries.coreCoroutines
import Dependencies.Libraries.kotlinReflect
import Dependencies.Libraries.kotlinStandardLibrary
import Dependencies.Versions.appCompatVersion
import Dependencies.Versions.constraintLayoutVersion
import Dependencies.Versions.coroutinesVersion
import Dependencies.Versions.daggerMockVersion
import Dependencies.Versions.daggerVersion
import Dependencies.Versions.espressoVersion
import Dependencies.Versions.gradleVersion
import Dependencies.Versions.jUnitVersion
import Dependencies.Versions.kluentVersion
import Dependencies.Versions.kotlinVersion
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
import Dependencies.Versions.workVersion
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Versions {

        //base gradle
        const val kotlinVersion = "1.3.50"
        const val objectboxVersion = "2.2.0"
        const val gradleVersion = "3.5.2"

        //Android
        const val androidMinSdkVersion = 21
        const val androidTargetSdkVersion = 29
        const val androidCompileSdkVersion = 29

        //Framework
        const val lifecycleVersion = "2.0.0"
        const val appCompatVersion = "1.0.2"
        const val supportLibraryVersion = "1.0.0"
        const val workVersion = "1.0.0-beta01"
        const val materialVersion = "1.0.0"
        const val navigationVersion = "2.2.0-rc02"
        const val androidXVersion = "1.0.0"
        const val pagingVersion = "2.1.0-rc01"

        //Other
        const val daggerVersion = "2.19"
        const val okHttpVersion = "3.12.0"
        const val retrofitVersion = "2.5.1-SNAPSHOT"
        const val glideVersion = "4.0.0"
        const val moshiVersion = "1.6.0"

        //Debugging
        const val timberVersion = "4.7.1"
        const val leakCanaryVersion = "1.6.1"

        //Rx
        const val coroutinesVersion = "1.1.1"

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
        const val mockitoKotlinVersion = "2.0.0"
        const val mockitoAndroidVersion = "2.21.0"
        const val runnerVersion = "1.1.0-alpha3"
        const val kluentVersion = "1.47"
        const val jacocoVersion = "0.8.2"
        const val livedataTestVersion = "1.0.0"
    }

    object Libraries {
        const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"

        const val threeTen = "com.jakewharton.threetenabp:threetenabp:$threeTenAndroidVersion"
        const val paging = "androidx.paging:paging-common:$lifecycleVersion"
        const val objectbox = "io.objectbox:objectbox-kotlin:$objectboxVersion"

        const val buildGradle = "com.android.tools.build:gradle:$gradleVersion"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val objectboxGradlePlugin = "io.objectbox:objectbox-gradle-plugin:$objectboxVersion"

        const val annotations = "androidx.annotation:annotation:$supportLibraryVersion"

        const val coreCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val androidCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }
}

fun DependencyHandler.implementNetworking() {
    add("implementation", "com.squareup.retrofit2:retrofit:$retrofitVersion")
    add("implementation", "com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    add("implementation", "com.squareup.moshi:moshi:$moshiVersion")
    add("implementation", "com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    add("implementation", "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    add("kapt", "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
}

fun DependencyHandler.implementTesting() {
    add("testImplementation", "junit:junit:$jUnitVersion")
    add("testImplementation", "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")
    add("testImplementation", "org.amshove.kluent:kluent:$kluentVersion")
    add("testImplementation", "org.mockito:mockito-inline:$mockitoAndroidVersion")
    add("testImplementation", "org.threeten:threetenbp:$threeTenJavaVersion")
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

fun DependencyHandler.implementKotlin() {
    add("implementation", kotlinStandardLibrary)
    add("implementation", kotlinReflect)
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
    add("testImplementation", "org.amshove.kluent:kluent-android:$kluentVersion")
    add("testImplementation", "com.squareup.leakcanary:leakcanary-android-no-op:$leakCanaryVersion")
    add("testImplementation", "androidx.paging:paging-common-ktx:$pagingVersion")
    add("testImplementation", "androidx.arch.core:core-testing:$lifecycleVersion")
    add("testImplementation", "com.jraska.livedata:testing-ktx:$livedataTestVersion")
}

fun DependencyHandler.implementAppAndroidTesting() {
    add("androidTestImplementation", "androidx.test.espresso:espresso-core:$espressoVersion")
    add("androidTestImplementation", "androidx.test.espresso:espresso-contrib:$espressoVersion")
    add("androidTestImplementation", "android.arch.work:work-testing:$workVersion")
    add("androidTestImplementation", "androidx.test:runner:$runnerVersion")
    add("androidTestImplementation", "androidx.test:rules:$runnerVersion")
    add("androidTestImplementation", "org.mockito:mockito-android:$mockitoAndroidVersion")
    add("androidTestImplementation", "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")
    add("androidTestImplementation", "com.squareup.okhttp3:mockwebserver:$okHttpVersion")
    add("androidTestImplementation", "androidx.arch.core:core-testing:$lifecycleVersion")
    add("androidTestImplementation", "com.google.dagger:dagger:$daggerVersion")
    add("androidTestImplementation", "com.github.fabioCollini.daggermock:daggermock-kotlin:$daggerMockVersion")
}