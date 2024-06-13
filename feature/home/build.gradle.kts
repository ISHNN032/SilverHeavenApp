import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.gradle.internal.testing.screenshot.PROPERTIES
import com.droidknights.app.setNamespace
import org.gradle.api.internal.properties.GradleProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("droidknights.android.feature")
}

val localProperties = Properties().apply {
    val inputStream = FileInputStream("local.properties")
    inputStream.use { this.load(it) }
}

android {
    setNamespace("feature.home")

    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField("String", "GPT_API_KEY", localProperties.getProperty("gpt.api.key") as String)
    }
}

dependencies {
    implementation(libs.kotlinx.immutable)
    implementation(libs.compose.shimmer)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.converter.gson)
    implementation(libs.gson)
}
