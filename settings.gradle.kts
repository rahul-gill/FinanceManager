pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }

        maven { setUrl("https://androidx.dev/storage/compose-compiler/repository/") }
    }
}

rootProject.name = "Finance Manager"
include(":app")
include(":core")