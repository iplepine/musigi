pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Musigi"
include(":app")
include(":domain")
include(":data")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":feature:miniplayer")
include(":feature:album")
include(":feature:library")
include(":feature:currentplaying")
include(":data:media")
