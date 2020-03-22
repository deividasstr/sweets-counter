plugins { kotlin }

sourceSets["main"].java.srcDir("src/main/kotlin")
sourceSets["test"].java.srcDir("src/test/kotlin")

dependencies {
    implementKotlin()
    implementation(Dependencies.Libraries.coreCoroutines)
    implementation(Dependencies.Libraries.threeTenAndroid)
    implementTesting()
}