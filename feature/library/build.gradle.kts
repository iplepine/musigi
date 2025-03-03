plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.zs.jyoon.library"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(projects.feature.miniplayer)

    // ✅ Core & Kotlin
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)

    // ✅ Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ✅ Hilt (Dependency Injection)
    implementation(libs.hilt)
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

    // ✅ 이미지 코일
    implementation(libs.coil.compose)

    // ✅ Testing (Unit & UI Tests)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // ✅ Debugging & UI Testing
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
