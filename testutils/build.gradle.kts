plugins { kotlin }

dependencies {
    add("compileOnly", project(":domain"))
    apiKotlin()
    implementation(Dependencies.Libraries.threeTenJava)
    implementation(Dependencies.Libraries.junit)
    implementation(Dependencies.Libraries.mockitoKotlin)
    implementation(Dependencies.Libraries.coreCoroutines)
    implementation(Dependencies.Libraries.testCoroutines)
}