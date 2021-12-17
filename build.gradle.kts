buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.fragmentKtx}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

//allprojects {
//    repositories {
//        google()
//        jcenter()
//        maven { url = uri("https://jitpack.io") }
//    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}