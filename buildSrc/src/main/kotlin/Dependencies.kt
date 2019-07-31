@file:Suppress("SpellCheckingInspection", "unused")

object Dependencies {

    private val versions = Versions

    object Versions {
        const val kotlin = "1.3.41"
        const val junit = "4.12"
        const val okHttp = "3.12.0"
        const val retrofit = "2.5.0"
        const val rxJava = "2.2.3"
        const val rxKotlin = "2.1.0"
        const val rxAndroid = "2.1.0"
        const val rxRelay = "2.0.0"
        const val glide = "4.9.0"
        const val playServices = "16.0.0"
        const val ktlint = "0.31.0"
        const val dagger = "2.16"
        const val navigation = "2.1.0-alpha04"
        const val lifecycle = "2.0.0"
        const val room = "2.1.0-alpha06"

        val androidX = AndroidX

        object AndroidX {

            const val multidex = "2.0.0"
            const val recyclerView = "1.0.0"
            const val archWork = "1.0.1"

            val test = Test

            object Test {
                const val junit = "1.1.0"
                const val runner = "1.1.1"
                const val espresso = "3.1.1"
            }
        }
    }

    const val junit = "junit:junit:${versions.junit}"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val androidGradlePlugin = "com.android.tools.build:gradle:3.4.1"

    val kotlin = Kotlin
    val androidX = AndroidX
    val google = Google
    val rx = Rx
//    val arello = Arello
    val bumptech = Bumptech
//    val stephanenicolas = Stephanenicolas
    val squareup = Squareup
//    val tinder = Tinder
//    val shyiko = Shyiko
//
//    object Shyiko {
//        const val ktlint = "com.github.shyiko.ktlint:${versions.ktlint}"
//        const val ktlintCore = "com.github.shyiko.ktlint:ktlint-core:${versions.ktlint}"
//        const val ktlintTest = "com.github.shyiko.ktlint:ktlint-test:${Dependencies.Versions.ktlint}"
//    }

    object Kotlin {
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.kotlin}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${versions.kotlin}"
    }

    object Bumptech {
        const val glide = "com.github.bumptech.glide:glide:${versions.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${versions.glide}"
//        const val glideOkHttpIntegration = "com.github.bumptech.glide:okhttp3-integration:${versions.glide}"
//        const val glideWebpDecoder = "com.zlc.glide:webpdecoder:1.5.${versions.glide}"
    }

    object Rx {
        const val java = "io.reactivex.rxjava2:rxjava:${versions.rxJava}"
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:${versions.rxKotlin}"
        const val android = "io.reactivex.rxjava2:rxandroid:${versions.rxAndroid}"
//        const val relay = "com.jakewharton.rxrelay2:rxrelay:${versions.rxRelay}"
    }

    object Squareup {
        const val retrofit = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
        const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofit}"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${versions.retrofit}"
//        const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${versions.leakcanary}"
//        const val leakcanaryNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:${versions.leakcanary}"
//        const val okHttp = "com.squareup.okhttp3:okhttp:${versions.okHttp}"
    }

    object AndroidX {

        const val appCompat = "androidx.appcompat:appcompat:1.0.2"
        const val coreKtx = "androidx.core:core-ktx:1.0.2"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
//        const val cardView = "androidx.cardview:cardview:1.0.0"
//        const val multidex = "androidx.multidex:multidex:${versions.androidX.multidex}"
//        const val fragment = "androidx.fragment:fragment:1.0.0"
//        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.0.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:${versions.androidX.recyclerView}"

//        val work = Work
        val room = Room
        val lifecycle = Lifecycle
        val navigation = Navigation
        val test = Test

//        object Work {
//            const val firebase = "android.arch.work:work-firebase:${versions.androidX.archWork}"
//            const val runtime = "android.arch.work:work-runtime:${versions.androidX.archWork}"
//            const val runtimeKtx = "android.arch.work:work-runtime-ktx:${versions.androidX.archWork}"
//            const val testing = "android.arch.work:work-testing:${versions.androidX.archWork}"
//        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${versions.room}"
            const val compiler = "androidx.room:room-compiler:${versions.room}"
            const val rxjava2 = "androidx.room:room-rxjava2:${versions.room}"
//            const val testing = "androidx.room:room-testing:${versions.room}"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime:${versions.lifecycle}"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:${versions.lifecycle}"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:${versions.lifecycle}"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycle}"
        }

        object Navigation {
            const val runtime = "androidx.navigation:navigation-runtime:${versions.navigation}"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:${versions.navigation}"
            const val fragment = "androidx.navigation:navigation-fragment:${versions.navigation}"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${versions.navigation}"
            const val ui = "androidx.navigation:navigation-ui:${versions.navigation}"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:${versions.navigation}"
            const val safeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.navigation}"
        }

        object Test {

            const val extJunit = "androidx.test.ext:junit:${versions.androidX.test.junit}"
            const val runner = "androidx.test:runner:${versions.androidX.test.runner}"

            val espresso = Espresso
            val ext = Ext

            object Ext {
                const val junit = "androidx.test.ext:junit:${versions.androidX.test.junit}"
                const val junitKtx = "androidx.test.ext:junit-ktx:${versions.androidX.test.junit}"
            }

            object Espresso {
                const val core = "androidx.test.espresso:espresso-core:${versions.androidX.test.espresso}"
            }
        }
    }

    object Google {

        const val material = "com.google.android.material:material:1.1.0-alpha05"
        const val gson = "com.google.code.gson:gson:2.8.5"
//
        val firebase = Firebase
        val dagger = Dagger

        object Firebase {
//            const val core = "com.google.firebase:firebase-core:16.0.9"
//            const val messaging = "com.google.firebase:firebase-messaging:18.0.0"
//            const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
//            const val config = "com.google.firebase:firebase-config:17.0.0"
        }

        object Dagger {
            const val runtime = "com.google.dagger:dagger:${versions.dagger}"
            const val android = "com.google.dagger:dagger-android:${versions.dagger}"
            const val androidSupport = "com.google.dagger:dagger-android-support:${versions.dagger}"
            const val compiler = "com.google.dagger:dagger-compiler:${versions.dagger}"
            const val androidSupportCompiler = "com.google.dagger:dagger-android-processor:${versions.dagger}"
        }
    }
}
