pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "A5_1_UIEventApp_starter"
include (":a_touchevent")
include (":a_touchevent_paint")
include (":b_clickevent")
include (":c_anonymousclass")
