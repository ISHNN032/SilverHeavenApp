plugins {
    id("droidknights.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.roborazzi.plugin)
    alias(libs.plugins.google.gms.google.services)


//    id("com.android.application")
//    id("com.google.gms.google-services")
//    kotlin("android")
}

android {
    namespace = "com.droidknights.app"

    compileSdk = 34

    defaultConfig {
        applicationId = "com.droidknights.app"
        versionCode = 1
        versionName = "1.0"

        minSdk = 28
        targetSdk = 34
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }

        create("benchmark") {
            matchingFallbacks.add("release")
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = false
        }
    }
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.feature.main)
    implementation(projects.feature.home)

    implementation(projects.core.designsystem)

    implementation(projects.widget)
    implementation(libs.firebase.auth)

    baselineProfile(projects.baselineprofile)
    implementation(libs.androidx.profileinstaller)

    testImplementation(projects.core.testing)



    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")

}
