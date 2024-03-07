plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.material3.android)
    coreLibraryDesugaring(libs.desugar.libs)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.tooling)
//        implementation(material3)
    implementation(libs.compose.material.icons.extended)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("dev.olshevski.navigation:reimagined:1.5.0")
    implementation("dev.olshevski.navigation:reimagined-material3:1.5.0")


    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.primitive.adapters)
    testImplementation(libs.sqldelight.test.driver)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("io.github.gill.rahul.financemanager.db")
        }
    }
}
