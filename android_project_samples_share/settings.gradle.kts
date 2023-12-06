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
rootProject.name = "android_project_samples_share"
include (":alarm_project")
include (":mouse_catch")
include (":speech_to_text")
include (":text_to_speech")
include (":xml_pull_parser")
include (":xml_pull_parser_hani_title")
