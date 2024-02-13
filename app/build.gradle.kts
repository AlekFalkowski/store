/**
 * https://developer.android.com/build
 */
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlinAndroid)

    /** Google Services Gradle. */
    alias(libs.plugins.google.gmsGoogleServices)

    /** KSP. */
    alias(libs.plugins.google.devtoolsKsp)

    /** HILT. */
    alias(libs.plugins.google.hiltAndroid)

    /** Kotlin Serialization. */
    alias(libs.plugins.jetbrains.kotlinPluginSerialization)
}

android {
    namespace = "dev.falkow.blanco"
    compileSdk = rootProject.extra["targetAndroidSdk"] as Int?
    // compileSdkPreview = rootProject.extra["compileSdkPreview"].toString()

    defaultConfig {
        applicationId = "dev.falkow.blanco"
        minSdk = rootProject.extra["minAndroidSdk"] as Int?
        targetSdk = rootProject.extra["targetAndroidSdk"] as Int?
        versionCode = 1
        versionName = "1.0"
        signingConfig = signingConfigs.getByName("debug")

        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // vectorDrawables {
        //     useSupportLibrary = true
        // }
    }

    buildTypes {
        // debug {
        //     isMinifyEnabled = true  // enables ProGuard
        //     // isShrinkResources = true
        //     proguardFiles(
        //         getDefaultProguardFile("proguard-android-optimize.txt"),
        //         "proguard-rules.pro"
        //     )
        // }
        release {
            isMinifyEnabled = true  // enables ProGuard
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Configure the build-logic plugins to target JDK 17
    // This matches the JDK used to build the project, and is not related to what is running on device.
    compileOptions {
        sourceCompatibility = rootProject.extra["javaVersion"] as JavaVersion
        targetCompatibility = rootProject.extra["javaVersion"] as JavaVersion
    }

    kotlinOptions {
        jvmTarget = rootProject.extra["jvmTarget"] as String
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion =
            rootProject.extra["kotlinCompilerExtensionVersion"] as String
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/*"
        }
    }
}

dependencies {

    /** HILT. */
    ksp(libs.google.hiltAndroidCompiler)
    implementation(libs.google.hiltAndroid)
    implementation(libs.androidx.hiltWork)
    implementation(libs.androidx.hiltNavigationCompose)

    /** Room. */
    ksp(libs.androidx.roomCompiler)
    implementation(libs.androidx.roomKtx)
    implementation(libs.androidx.roomRuntime)

    /**  */
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.composeBOM))
    implementation(libs.androidx.composeMaterial3)
    implementation(libs.androidx.composeMaterial3WindowSizeClass)
    implementation(libs.androidx.composeMaterialIconsExtended)
    implementation(libs.androidx.composeUi)
    implementation(libs.androidx.composeUiGraphics)
    implementation(libs.androidx.composeUiToolingPreview)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycleRuntimeKtx)
    implementation(libs.androidx.lifecycleRuntimeCompose) // For example: for collectAsStateWithLifecycle.
    implementation(libs.androidx.lifecycleViewModelCompose)
    implementation(libs.androidx.navigationCompose)

    /** Kotlin. */
    implementation(libs.jetbrains.kotlinxCoroutinesCore)
    implementation(libs.jetbrains.kotlinxCoroutinesAndroid)
    implementation(libs.jetbrains.kotlinxSerializationJson)

    /** Okhttp & Retrofit. */
    implementation(libs.squareup.okhttpCore)
    implementation(libs.squareup.okhttpLoggingInterceptor)
    implementation(libs.squareup.retrofitCore)
    implementation(libs.squareup.retrofitConverterScalars)
    implementation(libs.squareup.retrofitKotlinxSerializationConverter)

    /** DataStore. */
    // implementation(libs.androidx.dataStoreCore)
    // implementation(libs.androidx.dataStorePreferences)

    /** ?WorkManager. */
    implementation(libs.androidx.workKtx)

    /** Accompanist. */
    // implementation(libs.google.accompanistSystemuicontroller)

    /** Coil. */
    implementation(libs.coil.ktCoilCompose)
    implementation(libs.coil.ktCoilSvg)

    /** Firebase. */
    implementation(platform(libs.google.firebaseBOM))
    implementation(libs.google.firebaseAnalyticsKtx)
    implementation(libs.google.firebaseAuthKtx)
    implementation(libs.google.firebaseDatabaseKtx)
    implementation(libs.google.firebaseDynamicLinksKtx)
    implementation(libs.google.firebaseMessagingKtx)
    implementation(libs.google.firebaseStorageKtx)

    /** Test. */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExtJunit)
    androidTestImplementation(libs.androidx.testEspressoCore)
    androidTestImplementation(libs.androidx.composeUiTestJunit4)

    /** Debug. */
    debugImplementation(libs.androidx.composeUiTooling)
    debugImplementation(libs.androidx.composeUiTestManifest)
}