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
rootProject.name = "A12_CompContentProviderApp_starter"
include (":a_myprovider")
include (":b_myresolver")
include (":c_calllog")
include (":d_mediastore")
include (":e_contacts")