plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("com.android.tools.build:gradle:3.5.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
}