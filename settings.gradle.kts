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
rootProject.name = "taipeitour"

rootProject.name = "TaipeiTour"
include(":app")
include(":core:model")
include(":core:source")
include(":core:api")
include(":core:utils")
