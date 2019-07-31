import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

//apply {
//    from("script-git-version.gradle")
//}

val keystoreProperties = Properties().apply {
    val keyStoreFile = rootProject.file("keystore.properties")
    load(FileInputStream(keyStoreFile))
}

android {
    compileSdkVersion(Project.versions.sdk.compile)
    buildToolsVersion(Project.versions.buildTools)
    defaultConfig {
        applicationId = "com.tanyayuferova.lifestylenews"
        minSdkVersion(Project.versions.sdk.min)
        targetSdkVersion(Project.versions.sdk.target)
        versionCode = 1 // extra["gitVersionCode"] as? Int
        versionName = "0.0.1" // extra["gitVersionName"] as? String
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        all {
            buildConfigField("String", "API_KEY", keystoreProperties["apiKey"] as String)
        }
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    sourceSets {
        getByName("main").java.srcDir("src/main/kotlin")
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {

    //Kotlin
    implementation(Project.dependencies.kotlin.stdlibJdk7)

    // AndroidX
    implementation(Project.dependencies.androidX.appCompat)
    implementation(Project.dependencies.androidX.coreKtx)
    implementation(Project.dependencies.androidX.constraintLayout)
    implementation(Project.dependencies.androidX.recyclerView)

    // Lifecycle
    implementation(Project.dependencies.androidX.lifecycle.runtime)
    implementation(Project.dependencies.androidX.lifecycle.extensions)
    implementation(Project.dependencies.androidX.lifecycle.viewmodelKtx)
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Project.dependencies.androidX.lifecycle.compiler)

    // Navigation
    implementation(Project.dependencies.androidX.navigation.fragment)
    implementation(Project.dependencies.androidX.navigation.fragmentKtx)

    // Room
    implementation(Project.dependencies.androidX.room.runtime)
    implementation(Project.dependencies.androidX.room.rxjava2)
    kapt(Project.dependencies.androidX.room.compiler)

    // RX
    implementation(Project.dependencies.rx.java)
    implementation(Project.dependencies.rx.kotlin)
    implementation(Project.dependencies.rx.android)

    // Dagger2
    implementation(Project.dependencies.google.dagger.runtime)
    implementation(Project.dependencies.google.dagger.android)
    implementation(Project.dependencies.google.dagger.androidSupport)
    kapt(Project.dependencies.google.dagger.compiler)
    kapt(Project.dependencies.google.dagger.androidSupportCompiler)

    // Glide
    implementation(Project.dependencies.bumptech.glide)
    kapt(Project.dependencies.bumptech.glideCompiler)

    // Retrofit
    implementation(Project.dependencies.squareup.retrofit)
    implementation(Project.dependencies.squareup.retrofitGsonConverter)
    implementation(Project.dependencies.squareup.retrofitRxAdapter)

    // Google
    implementation(Project.dependencies.google.material)
    implementation(Project.dependencies.google.gson)

    // Debug
    debugImplementation(Project.dependencies.timber)

    // Test
    testImplementation(Project.dependencies.androidX.test.extJunit)

    // Android Test
    androidTestImplementation(Project.dependencies.androidX.test.runner)
    androidTestImplementation(Project.dependencies.androidX.test.espresso.core)
}
