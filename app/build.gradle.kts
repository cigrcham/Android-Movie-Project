plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.gms.google.services)
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.androidmoviesproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.androidmoviesproject"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "KEY_API_MOVIE_TMDB", "\"07862ec487e8e38c7f93d2b24920dee8\"")
        }

        release {
            buildConfigField("String", "KEY_API_MOVIE_TMDB", "\"07862ec487e8e38c7f93d2b24920dee8\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.material)
    testImplementation(libs.junit)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.okhttp)
    implementation(libs.retrofit.plugin)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.logging.interceptor.v500alpha14)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.gradle.plugin)

    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    androidTestImplementation(libs.navigation.testing)
    implementation(libs.navigation.dynamic.features.fragment)

    implementation(libs.coil)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.database.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.google.firebase.auth.ktx)

    implementation(libs.gson)
    implementation(libs.room.ktx)
    implementation(libs.room.guava)
    implementation(libs.room.paging)
    implementation(libs.room.runtime)
    implementation(libs.room.rxjava2)
    implementation(libs.room.rxjava3)
    testImplementation(libs.room.testing)

    implementation(libs.shimmer)

    implementation(libs.viewmodel.ktx)
    implementation(libs.viewmodel.service)
    implementation(libs.viewmodel.process)
    implementation(libs.viewmodel.savedstate)
    implementation(libs.viewmodel.livedata.ktx)
    implementation(libs.viewmodel.reactivestreams.ktx)
    testImplementation(libs.viewmodel.runtime.testing)
    implementation(libs.androidx.lifecycle.common.java8)
}