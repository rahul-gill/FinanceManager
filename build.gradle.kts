plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.detekt)
}

detekt {
    parallel = true
    config.setFrom("${projectDir.absolutePath}/config/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    // baseline = file("path/to/baseline.xml")
    basePath = projectDir.absolutePath
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        xml.required.set(false)
        txt.required.set(false)
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))
        sarif.required.set(true)
        sarif.outputLocation.set(file("build/reports/detekt.sarif"))
    }
    basePath = projectDir.absolutePath
}

dependencies {
    detektPlugins(libs.detekt.compose.rules)
}
