buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri( "https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}