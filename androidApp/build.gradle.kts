import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.thinlineit.favorit_android.android"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "KAKAO_API_KEY", getApiKey("KAKAO_API_KEY"))
        manifestPlaceholders["KAKAO_API_KEY"] = getApiKey("KAKAO_API_KEY")
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

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.navigation:navigation-fragment:2.3.5")
    val LIFECYCLE_VERSION = "2.5.0-rc01"
    val RETROFIT_VERSION = "2.9.0"
    val OKHTTP_VERSION = "4.9.1"
    val GLIDE_VERSION = "4.13.0"
    val HILT_VERSION = "2.42"

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
    implementation("com.github.bumptech.glide:glide:$GLIDE_VERSION")
    annotationProcessor("com.github.bumptech.glide:compiler:$GLIDE_VERSION")

    //Hilt
    implementation ("com.google.dagger:hilt-android:$HILT_VERSION")
    kapt("com.google.dagger:hilt-android-compiler:$HILT_VERSION")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
}