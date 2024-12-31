plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-kapt")
}

android {
    namespace = "com.dima6120.core_impl"
    compileSdk = 34

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField("String", "OAUTH_ENDPONT", "\"https://myanimelist.net/v1/oauth2/\"")
        buildConfigField("String", "API_ENDPONT", "\"https://api.myanimelist.net/v2/\"")
        buildConfigField("String", "CLIENT_ID", "\"7222db061f717d844f038dab2777175b\"")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(project(":core-api"))

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.kotlinxSerializationConverter)
    implementation(libs.squareup.okhttp.loggingInterceptor)
}