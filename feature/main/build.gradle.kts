import com.droidknights.app.setNamespace

plugins {
    id("droidknights.android.feature")
}

android {
    setNamespace("feature.main")

    defaultConfig {
        testInstrumentationRunner =
            "com.droidknights.app.core.testing.runner.DroidKnightsTestRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(projects.feature.home)
    implementation(projects.feature.setting)
    implementation(projects.feature.contributor)
    implementation(projects.feature.session)
    implementation(projects.feature.bookmark)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.fragment)
    implementation(libs.gson)
    androidTestImplementation(projects.core.testing)
    debugImplementation(projects.core.uiTestHiltManifest)

    implementation(projects.widget)
    implementation(projects.core.dataApi)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.kotlinx.immutable)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
}
