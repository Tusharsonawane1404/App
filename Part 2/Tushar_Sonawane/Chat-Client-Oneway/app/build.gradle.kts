plugins {
    id("com.android.application")
}

android {
    namespace = "edu.stevens.cs522.chatclient"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.stevens.cs522.chatclient"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(files("libs/cs522-library.aar"))
    implementation("com.google.guava:guava:33.0.0-android")
}