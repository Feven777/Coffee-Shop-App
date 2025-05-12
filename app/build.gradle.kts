plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.example.shopApp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shopApp"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}
dependencies {
    implementation (libs.androidx.core.ktx.v190)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)

    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    coreLibraryDesugaring (libs.desugar.jdk.libs)
    implementation (libs.androidx.core.ktx.v1120)
    implementation (libs.androidx.activity.compose.v182)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.material3)

    implementation (libs.androidx.datastore.preferences)
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.logging.interceptor)

    implementation (platform(libs.androidx.compose.bom.v20230800))
    implementation (libs.ui)

    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.material3.v112)
    implementation (libs.kotlinx.coroutines.core)

    implementation (libs.androidx.compose.ui.ui)
    implementation (libs.ui.graphics)
    implementation (libs.ui.tooling.preview)
    implementation (libs.material3)
    implementation (libs.androidx.navigation.compose.v277)
    implementation (libs.androidx.lifecycle.viewmodel.compose.v262)
    implementation(libs.androidx.material3.android)
    implementation(libs.ads.mobile.sdk)

    // Debug dependencies
    debugImplementation (libs.ui.tooling)
    debugImplementation (libs.ui.test.manifest)
}