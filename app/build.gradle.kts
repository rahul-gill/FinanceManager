plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "io.github.gill.rahul.financemanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.gill.rahul.financemanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.libs)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    with(libs.compose) {
        implementation(platform(bom))
        implementation(ui.tooling)
        implementation(material3)
        implementation(material.icons.extended)
        androidTestImplementation(platform(bom))
        androidTestImplementation(ui.test.junit4)
        debugImplementation(ui.tooling)
        debugImplementation(ui.test.manifest)
    }

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    with(libs.compose.destination) {
        implementation(core)
        ksp(ksp)
    }

    with(libs.multiplatform.settings) {
        implementation(no.arg)
        implementation(coroutines)
    }

    with(libs.sqldelight){
        implementation(android.driver)
        implementation(primitive.adapters)

    }
}



sqldelight {
    databases {
        create("Database") {
            packageName.set("io.github.gill.rahul.financemanager.db")
        }
    }
}
