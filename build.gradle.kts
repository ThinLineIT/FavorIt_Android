buildscript {
    val compose_version by extra("1.2.0")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
