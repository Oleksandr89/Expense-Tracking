plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    //modules
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":balance:domain"))

    //core
    implementation(libs.androidx.core.ktx)

    //di
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //network
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}