import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 33
    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.thinlineit.favorit_android.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "KAKAO_API_KEY", "\"${getApiKey("KAKAO_API_KEY")}\"")
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.navigation:navigation-fragment:2.3.5")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    val LIFECYCLE_VERSION = "2.5.0-rc01"
    val RETROFIT_VERSION = "2.9.0"
    val OKHTTP_VERSION = "4.9.1"
    val GLIDE_VERSION = "4.14.1"
    val HILT_VERSION = "2.42"

    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.activity:activity:1.6.0-rc01")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // kakao login
    implementation("com.kakao.sdk:v2-user:2.10.0")

    // viewModel & livedata
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION")
    implementation("androidx.compose.runtime:runtime-livedata:${rootProject.extra["compose_version"]}")
    kapt("androidx.lifecycle:lifecycle-compiler:$LIFECYCLE_VERSION")

    // ktx
    implementation("androidx.activity:activity-ktx:1.4.0")

    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:$OKHTTP_VERSION")
    implementation("com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:$OKHTTP_VERSION")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    // gson
    implementation("com.google.code.gson:gson:2.8.9")

    // Glide
    implementation("com.github.bumptech.glide:glide:$GLIDE_VERSION")
    annotationProcessor("com.github.bumptech.glide:compiler:$GLIDE_VERSION")
    implementation("com.github.skydoves:landscapist-glide:1.4.7")
    implementation("com.github.skydoves:landscapist-coil:1.4.7")
    implementation( "com.github.bumptech.glide:okhttp3-integration:$GLIDE_VERSION")
    kapt("com.github.bumptech.glide:compiler:$GLIDE_VERSION")

    // Hilt
    implementation("com.google.dagger:hilt-android:$HILT_VERSION")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")
    kapt("com.google.dagger:hilt-android-compiler:$HILT_VERSION")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
}
