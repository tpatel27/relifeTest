import com.android.build.gradle.internal.dsl.DefaultConfig
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
}

val propertiesFile = rootProject.file("Project.properties")
val properties = Properties()
properties.load(FileInputStream(propertiesFile))

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId(applicationId = AppConfig.appId)
        minSdkVersion(minSdkVersion = AppConfig.minSdk)
        targetSdkVersion(targetSdkVersion = AppConfig.targetSdk)
        versionCode(versionCode = AppConfig.appVersionCode)
        versionName(versionName = AppConfig.appVersionName)

        setProperty("archivesBaseName", getArtifactName(artifact = this))

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(
                    hashMapOf(
                        "room.schemaLocation" to "$projectDir/schemas"
                    )
                )
            }
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions("default")
    productFlavors {
        create("staging") {
            buildConfigField(
                "String",
                "USER_BASE_URL",
                properties["STAGING_USER_BASE_URL"].toString()
            )
        }
        create("production") {
            buildConfigField(
                "String",
                "USER_BASE_URL",
                properties["PRODUCTION_USER_BASE_URL"].toString()
            )
        }
    }

    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures.dataBinding = true

}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    //kotlin-stdlib
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")

    //local/app/module libs
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //android core
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.appcompat:appcompat:${Versions.appCompact}")
    implementation("androidx.legacy:legacy-support-v4:${Versions.legacySupport}")

    //android ui
    implementation("com.google.android.material:material:${Versions.material}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")

    //android navigation architecture
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.fragmentKtx}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.fragmentKtx}")

    //android lifecycle components
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleKtx}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}")

    //retrofit and interceptor
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofit}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}")

    //kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    //glide image loader
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")

    //dagger hilt
    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    kapt("com.google.dagger:hilt-compiler:${Versions.hilt}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    //android roomdb
    implementation("androidx.room:room-runtime:${Versions.room}")
    implementation("androidx.room:room-ktx:${Versions.room}")
    kapt("androidx.room:room-compiler:${Versions.room}")

    //android pagination
    implementation("androidx.paging:paging-runtime:${Versions.paging}")
    implementation("androidx.paging:paging-runtime-ktx:${Versions.paging}")

    //firebase-bom
//    implementation(platform("com.google.firebase:firebase-bom:26.5.0"))
//    implementation("com.google.firebase:firebase-messaging-ktx")
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-crashlytics-ktx")

    //permissions handler
    implementation("com.github.quickpermissions:quickpermissions-kotlin:${Versions.quickpermissions}")

    //timber logger
    implementation("com.jakewharton.timber:timber:${Versions.timber}")

    //pinview
    implementation("com.github.aabhasr1:OtpView:${Versions.pinView}")


    //test libs
    //junit
    testImplementation("junit:junit:${Versions.jUnit}")
    //android tests
    androidTestImplementation("androidx.test.ext:junit:${Versions.jUnitExt}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")

}

fun getArtifactName(artifact: DefaultConfig): String {
    val date = DateTimeFormatter.ofPattern("MMM-dd-yyyy").format(LocalDate.now()).toString()
    return project.name + "-v" + "(" + artifact.versionName + ")" + "-code" + "(" + artifact.versionCode + ")" + "-" + date
}