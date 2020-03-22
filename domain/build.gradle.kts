plugins { kotlin }

sourceSets["main"].java.srcDir("src/main/kotlin")
sourceSets["test"].java.srcDir("src/test/kotlin")

dependencies {
    apiKotlin()
    api(Dependencies.Libraries.coreCoroutines)
    api(Dependencies.Libraries.threeTenAndroid)
    api(Dependencies.Libraries.timber)
    implementTesting()
}
