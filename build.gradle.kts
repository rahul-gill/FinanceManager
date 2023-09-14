import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.detekt)
}

detekt {
    basePath = rootProject.projectDir.absolutePath
    parallel = true
    config.setFrom("$rootDir/config/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    source.setFrom(projectDir)
    // baseline = file("path/to/baseline.xml")
}

tasks.withType<Detekt>().configureEach {
    exclude("**/build/**")
    reports {
        xml.required.set(false)
        txt.required.set(false)
        html.required.set(true)
        sarif.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))
        sarif.outputLocation.set(file("build/reports/detekt.sarif"))
    }
}

dependencies {
    detektPlugins(libs.detekt.compose.rules)
}
