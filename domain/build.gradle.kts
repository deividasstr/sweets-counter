plugins {
    kotlin
    id("kotlin-allopen")
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-allopen:1.3.50")
    }
}

allOpen {
    annotation ("com.deividasstr.domain.utils.OpenClass")
}

sourceSets["main"].java.srcDir("src/main/kotlin")
sourceSets["test"].java.srcDir("src/test/kotlin")

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementKotlin()

    implementation(Dependencies.Libraries.coreCoroutines)
    implementation(Dependencies.Libraries.threeTen)

    implementTesting()
}

/*sourceCompatibility = "1.8"
targetCompatibility = "1.8"*/