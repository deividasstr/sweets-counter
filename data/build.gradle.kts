plugins {
    `android-library`
    id("baselibplugin")
    id("io.objectbox")
}

android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("objectbox.incremental" to "true")
            }
        }
    }
}

dependencies {
    implementNetworking()
    implementDagger()

    implementation(Dependencies.Libraries.paging) // FIXME: remove?
    implementation(Dependencies.Plugins.objectbox)

    androidTestImplementation(Dependencies.Libraries.annotations)
}
