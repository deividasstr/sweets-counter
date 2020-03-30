plugins {
    `android-library`
    id("baselibplugin")
    id("com.squareup.sqldelight")
}

dependencies {
    implementNetworking()
    implementDagger()

    implementation(Dependencies.Libraries.paging)
    implementation(Dependencies.Libraries.sqlDelight)
    implementation(Dependencies.Libraries.sqlDelightPaging)

    testImplementation(Dependencies.Libraries.sqlDelightJvm)
    androidTestImplementation(Dependencies.Libraries.annotations)
}
