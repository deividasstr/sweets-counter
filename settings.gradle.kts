import de.fayard.dependencies.bootstrapRefreshVersionsAndDependencies

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies.classpath("de.fayard:dependencies:0.5.7")
}

bootstrapRefreshVersionsAndDependencies()

include(":ui", ":domain", ":data", ":MPChartLib", ":testutils")
