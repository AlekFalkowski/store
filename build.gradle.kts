plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlinJvm) apply false
    alias(libs.plugins.jetbrains.kotlinAndroid) apply false

    /** Google Services Gradle. */
    alias(libs.plugins.google.gmsGoogleServices) apply false

    /** KSP. */
    alias(libs.plugins.google.devtoolsKsp) apply false

    /** HILT. */
    alias(libs.plugins.google.hiltAndroid) apply false

    /** Kotlin Serialization. */
    alias(libs.plugins.jetbrains.kotlinPluginSerialization) apply false
}

extra["minAndroidSdk"] = 28 // Ключи доступа поддерживаются только на устройствах под управлением Android 9 (уровень API 28).
extra["targetAndroidSdk"] = 34
extra["compileSdkPreview"] = "UpsideDownCake"
extra["javaVersion"] = JavaVersion.VERSION_17 // https://developer.android.com/build/jdks
extra["jvmTarget"] = "17"
extra["kotlinCompilerExtensionVersion"] = "1.5.9" // https://developer.android.com/jetpack/androidx/releases/compose-compiler