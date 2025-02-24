plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.8.7" apply false
    id ("com.google.dagger.hilt.android") version "2.54" apply false // Hilt
}