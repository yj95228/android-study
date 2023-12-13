plugins {
    id("com.android.library")
}

android {
    namespace = "uk.co.alt236.bluetoothlelib"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.txt"))
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}