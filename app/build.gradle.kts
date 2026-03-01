plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "villegas.marco.proyecto_final"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "villegas.marco.proyecto_final"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Librería principal de Media3 para ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    // UI components (PlayerView para XML)
    implementation("androidx.media3:media3-ui:1.5.1")
    // Soporte para formatos comunes (Dash, HLS, etc.)
    implementation("androidx.media3:media3-common:1.5.1")
    // LottieFiles
    implementation("com.airbnb.android:lottie:6.4.1")
}