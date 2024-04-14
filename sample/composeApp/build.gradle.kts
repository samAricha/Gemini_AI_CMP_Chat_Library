import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.buildKonfig) apply true

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation("io.github.samaricha:chatSDK:0.0.1")


//            implementation(project(":chatSDK"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "org.teka.gemini_ai_cmp_chat_library"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.teka.gemini_ai_cmp_chat_library"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.teka.gemini_ai_cmp_chat_library"
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "org.teka.gemini_ai_cmp_chat_library"

    defaultConfigs {
        val apiKey: String = gradleLocalProperties(rootDir).getProperty("API_KEY")
        val sonatypeUsername: String = gradleLocalProperties(rootDir).getProperty("SONATYPE_USERNAME")
        val sonatypePassword: String = gradleLocalProperties(rootDir).getProperty("SONATYPE_PASSWORD")

        require(apiKey.isNotEmpty()) {
            "Register your api key from developer and place it in local.properties as `API_KEY`"
        }

        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "API_KEY", apiKey)
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "SONATYPE_USERNAME", sonatypeUsername)
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "SONATYPE_PASSWORD", sonatypePassword)
    }
}
