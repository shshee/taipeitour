@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.tangerine.core.data"
    compileSdk = libs.versions.sdk.target.get().toInt()

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.app.compat)
    implementation(libs.material.design)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.android.test.junit)
    androidTestImplementation(libs.android.test.espresso)
}