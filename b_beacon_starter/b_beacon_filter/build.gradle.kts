plugins {
    id ("com.android.application")
}

android {
    namespace = "edu.jaen.android.beacon_filter"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.jaen.android.beacon_filter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.txt"))
        }
    }
    viewBinding{
        enable = true
    }
}
dependencies {

    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(path = ":z_beacon_bluetoothLELibrary"))
}