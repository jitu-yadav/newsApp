import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

val newsApiKey: String =
    gradleLocalProperties(rootDir, providers).getProperty("NEWS_API_KEY") ?: ""

android {
    namespace = "com.stupid.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.stupid.newsapp"
        minSdk = 24
        targetSdk = 35
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

hilt {
    enableAggregatingTask = false
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

    // ➤ Correct Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)

    // Networking
    implementation(libs.bundles.networking)

    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // Room
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    // Paging
    implementation(libs.bundles.paging)

    // Coil
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.bundles.coroutines)
}