plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.thinlineit.favorit_android.android"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    val LIFECYCLE_VERSION = "2.5.0-rc01"
    val RETROFIT_VERSION = "2.9.0"
    val OKHTTP_VERSION = "4.9.1"
    val GLIDE_VERSIOON = "4.13.0"

    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    //kakao login
    implementation("com.kakao.sdk:v2-user:2.10.0")

    //viewModel & livedata
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION")

    //ktx
    implementation("androidx.activity:activity-ktx:1.4.0")

    //Retrofit2
    implementation("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION")

    //okhttp
    implementation("com.squareup.okhttp3:okhttp:$OKHTTP_VERSION")
    implementation("com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:$OKHTTP_VERSION")

    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    //gson
    implementation("com.google.code.gson:gson:2.8.9")

    //Glide
    implementation("com.github.bumptech.glide:glide:$GLIDE_VERSIOON")
    annotationProcessor("com.github.bumptech.glide:compiler:$GLIDE_VERSIOON")
}