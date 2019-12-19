apply<BaseLibPlugin>()

plugins {
    id("com.android.library")
    id("baselibplugin")
    id("io.objectbox")
}

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-allopen:${Dependencies.Versions.kotlinVersion}")
    }
}

/*allOpen {
    annotation ("com.deividasstr.data.utils.OpenClass")
}*/

android {
    sourceSets.getByName("debug").java.srcDir("src/debug/kotlin")
    sourceSets.getByName("release").java.srcDir("src/release/kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementNetworking()
    implementDagger()

    implementation (Dependencies.Libraries.paging)
    implementation (Dependencies.Libraries.objectbox)

    androidTestImplementation(Dependencies.Libraries.annotations)
}