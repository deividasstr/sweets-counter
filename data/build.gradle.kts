plugins {
    `android-library`
    id("baselibplugin")
    id("io.objectbox")
}

dependencies {
    implementNetworking()
    implementDagger()

    implementation(Dependencies.Libraries.paging) // FIXME: remove?
    implementation(Dependencies.Libraries.objectbox)

    androidTestImplementation(Dependencies.Libraries.annotations)
}
