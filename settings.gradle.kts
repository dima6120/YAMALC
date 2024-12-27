import java.net.URI

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
        maven { url = URI("https://jitpack.io") }
    }
}

rootProject.name = "YAMALC"
include(":app")
include(":core")
include(":core-api")
include(":core-impl")
include(":profile")
include(":profile-api")
include(":main")
include(":main-api")
include(":search")
include(":search-api")
include(":anime-lists")
include(":anime-lists-api")
include(":anime-title")
include(":anime-title-api")
include(":splash")
include(":splash-api")
include(":ui")
