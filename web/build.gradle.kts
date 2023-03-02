plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.3.0"
}

version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
    google()
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport { enabled.set(true) }
                scssSupport { enabled.set(true) }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)

//                implementation("org.jetbrains:kotlin-extensions:1.0.1-pre.148-kotlin-1.4.21")
//                implementation(npm("postcss", "8.2.6"))
//                implementation(npm("postcss-loader", "4.2.0")) // 5.0.0 seems not to work
//                implementation(npm("autoprefixer", "10.2.4"))
//                implementation(npm("tailwindcss", "2.0.3"))

//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.201-kotlin-1.5.0")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.201-kotlin-1.5.0")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.0-pre.201-kotlin-1.5.0")
//
//                implementation(npm("react", "17.0.2"))
//                implementation(npm("react-dom", "17.0.2"))
//                implementation(npm("react-youtube-lite", "1.0.1"))

                // Be lazy and use the shortcut
//                implementation("dev.petuska:kmdc:0.1.0")
//                implementation("dev.petuska:kmdcx:0.1.0")

                implementation(project(":shared"))
            }
        }
    }
}

// workaround for https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}