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

rootProject.name = "Expense Tracking"
include(":app")
include(":core:common")
include(":core:designsystem")
include(":core:database")
include(":core:network")
include(":core:domain")
include(":core:data")
include(":balance:presentation")
include(":balance:domain")
include(":balance:data")
include(":transaction:presentation")
