pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "b_beacon_starter"
include (":a_beacon_basic")
include (":b_beacon_filter")
include (":c_beacon_altbeacon")
include (":z_beacon_bluetoothLELibrary")
