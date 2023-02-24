pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.google.cloud.tools.appengine")) {
                useModule("com.google.cloud.tools:appengine-gradle-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
//        maven(url = "https://mvnrepository.com/artifact/org.jetbrains.kotlin-wrappers/kotlin-styled")
        maven(url = "https://jitpack.io")
//        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
    }
}

rootProject.name = "saleor-mobile"
include(":androidApp")
include(":shared")
include(":web")