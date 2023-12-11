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

rootProject.name = "a_nfc_starter"
include (":a_tag_recognition")
include (":b_tag_recognition_foreground")
include (":c_tag_reader")
include (":d_tag_writer")