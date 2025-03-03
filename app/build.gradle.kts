plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.zs.jyoon.musigi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zs.jyoon.musigi"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    composeCompiler {
        stabilityConfigurationFile =
            rootProject.layout.projectDirectory.file("stability_config.conf")
    }
}
dependencies {
    // ✅ 프로젝트 모듈
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.data.media)
    implementation(projects.feature.album)
    implementation(projects.feature.library)
    implementation(projects.feature.miniplayer)
    implementation(projects.feature.currentplaying)

    // ✅ Core & Kotlin
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)

    // ✅ Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ✅ Hilt (Dependency Injection)
    implementation(libs.hilt)
    implementation(libs.androidx.lifecycle.process)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // ✅ Jetpack Compose (UI 관련)
    implementation(platform(libs.androidx.compose.bom)) // ✅ Compose BOM 관리
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // exoplayer
    implementation(libs.androidx.media3.exoplayer) // ✅ ExoPlayer 핵심 라이브러리
    implementation(libs.androidx.media3.ui) // ✅ ExoPlayer UI (선택 사항)
    implementation(libs.androidx.media3.session) // ✅ MediaSession 지원 (선택 사항)

    // ✅ Testing (Unit & UI Tests)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // ✅ Debugging & UI Testing
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
