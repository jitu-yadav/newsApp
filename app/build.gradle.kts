import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    kotlin("kapt")
}
val newsApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("NEWS_API_KEY") ?: ""

android {
    namespace = "com.stupid.newsapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.stupid.newsapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work) {
        exclude(group = "androidx.hilt", module = "hilt-compiler")
    }
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // Networking
    implementation(libs.bundles.networking)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Paging
    implementation(libs.bundles.paging)

    // Coil
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // --- FIX START ---
    // Force the specific JavaPoet version for both KAPT and KSP to resolve the conflict
    // You don't need to declare this in libs.versions.toml if you just want a quick fix,
    // otherwise add it there and use libs.javapoet.
    val javapoet = "com.squareup:javapoet:1.13.0"
    kapt(javapoet)

}
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}

// ADD THIS BLOCK to specifically force KSP configurations
// This is the "nuclear option" for KSP conflicts
configurations.filter { it.name.startsWith("ksp") }.forEach {
    it.resolutionStrategy.force("com.squareup:javapoet:1.13.0")
}
