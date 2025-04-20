plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.expensetracking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.expensetracking"
        minSdk = 28
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //modules
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":balance:presentation"))
    implementation(project(":balance:domain"))
    implementation(project(":balance:data"))
    implementation(project(":transaction:presentation"))

    //core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    //di
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // serialization
    implementation(libs.kotlinx.serialization)

    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}