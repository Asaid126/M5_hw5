

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.m5_hw4"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.m5_hw4"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
        }
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
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.expandablelayout)

    //retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //coil
    implementation (libs.coil)

    //ViewBinding Delegate
    implementation(libs.vbpd)  //нужно про нее почитать подробнее


    //navigation
    implementation (libs.androidx.navigation.fragment)
    implementation (libs.androidx.navigation.ui)

    //Glide
    implementation (libs.glide)
    //D-Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)



}

////retrofit
//implementation ("com.squareup.retrofit2:retrofit:2.11.0")
//implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

////coil
//implementation ("io.coil-kt:coil:0.9.1")

//// navigation
//implementation "androidx.navigation:navigation-fragment:$nav_version"
//implementation "androidx.navigation:navigation-ui:$nav_version"

////room
//implementation("androidx.room:room-runtime:2.6.1")
//kapt("androidx.room:room-compiler:2.6.1")

