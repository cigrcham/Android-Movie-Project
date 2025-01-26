// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.8.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.gms.google.services) apply false
    alias(libs.plugins.navigation.safe.args) apply false
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.serialization) apply false
}