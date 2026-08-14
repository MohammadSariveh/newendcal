plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
}
android {
    namespace = "com.example.serviceend"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.serviceend"
        minSdk = 23
        targetSdk = 35
        versionCode = 3
        versionName = "3.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
