import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion = "1.8.0"
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("js")
    val kvisionVersion: String by System.getProperties()
    id("io.kvision") version kvisionVersion
}

version = "1.0.0-SNAPSHOT"
group = "com.musafira2z"

repositories {
    mavenCentral()
    mavenLocal()
}

// Versions
val kotlinVersion = "1.8.0"
val kvisionVersion: String by System.getProperties()

val webDir = file("src/main/web")

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
            }
            runTask {
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            }
//            testTask {
//                useKarma {
//                    useChromeHeadless()
//                }
//            }
        }
        binaries.executable()
    }
    sourceSets["main"].dependencies {
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
        implementation("io.kvision:kvision-tom-select:$kvisionVersion")
        implementation("io.kvision:kvision-imask:$kvisionVersion")
        implementation("io.kvision:kvision-toastify:$kvisionVersion")
        implementation("io.kvision:kvision-fontawesome:$kvisionVersion")
        implementation("io.kvision:kvision-routing-ballast:$kvisionVersion")
        implementation("io.kvision:kvision-ballast:$kvisionVersion")
        implementation(project(":shared"))
    }
//    sourceSets["test"].dependencies {
//        implementation(kotlin("test-js"))
//        implementation("io.kvision:kvision-testutils:$kvisionVersion")
//    }
    sourceSets["main"].resources.srcDir(webDir)
}
