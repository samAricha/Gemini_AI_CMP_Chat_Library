import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import com.vanniktech.maven.publish.SonatypeHost


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)

    alias(libs.plugins.nmcp)
    alias(libs.plugins.mavenPublishVanniktech)
    alias(libs.plugins.dokka)
    id("signing")

    kotlin("plugin.serialization") version "1.9.21"

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

            implementation(libs.generativeai)
            implementation(libs.kamel.image)
            implementation(libs.mvvm.core)
            implementation(libs.mvvm.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.multiplatform.markdown.renderer)

            // For Adaptive FilePicker
            implementation(libs.calf.file.picker)

            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
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
        minSdk = libs.versions.android.minSdk.get().toInt()
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


group = "io.github.samaricha"
version = "0.0.1"
//version = properties["version"] as String

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    pom {
        name.set("Gemini_AI_CMP_Chat_Library")
        description.set("Compose Multiplatform Gemini AI Chat Library")
        url.set("https://github.com/samAricha/ImagePreviewCMPLibrary")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        issueManagement {
            system.set("GitHub Issues")
            url.set("https://github.com/samAricha/ImagePreviewCMPLibrary/issues")
        }

        developers {
            developer {
                id.set("samAricha")
                name.set("Aricha Samson")
                email.set("samaricha001@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com:samAricha/ImagePreviewCMPLibrary.git")
            developerConnection.set("scm:git:ssh://github.com:samAricha/ImagePreviewCMPLibrary.git")
            url.set("https://github.com/samAricha/ImagePreviewCMPLibrary")
        }
    }
}

nmcp {
    publishAllPublications {
        // get from ~/.gradle/gradle.properties (HOME/.gradle/gradle.properties)
        username = gradleLocalProperties(rootDir).getProperty("SONATYPE_USERNAME")
        password = gradleLocalProperties(rootDir).getProperty("SONATYPE_PASSWORD")
        publicationType = "AUTOMATIC"
    }
}



