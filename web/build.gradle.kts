plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.3.1"
    kotlin("plugin.serialization") version "1.8.10"
}

version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.web.svg)
                implementation(compose.runtime)

                implementation(devNpm("tailwindcss", "3.2.7"))
                implementation(devNpm("postcss", "8.4.21"))
                implementation(devNpm("autoprefixer", "10.4.14"))
                implementation(devNpm("postcss-loader", "7.0.2"))
                implementation(npm("flowbite", "1.6.4"))
                implementation(npm("editorjs-parser", "1.5.3"))

                implementation(project(":shared"))
            }
        }
    }
}

val copyTailwindConfig = tasks.register<Copy>("copyTailwindConfig") {
    from("${rootProject.rootDir}/tailwind.config.js")
    into("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")

    dependsOn(":kotlinNpmInstall")
}

val runTailwindProductionPack = tasks.register<Exec>("runTailwindProductionPack") {

    workingDir("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")

    commandLine(
        "npx",
        "tailwindcss",
        "-i",
        "./kotlin/app.css",
        "-o",
        "./kotlin/tailwind.css"
    )

    dependsOn(":web:compileKotlinJs")
}

//val copyPostcssConfig = tasks.register<Copy>("copyPostcssConfig") {
//    from("${rootProject.rootDir}/postcss.config.js")
//    into("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")
//
//    dependsOn(":kotlinNpmInstall")
//}

tasks.named("jsProcessResources") {
    dependsOn(copyTailwindConfig)
}

tasks.named("jsBrowserProductionWebpack") {
//    dependsOn(runTailwindProductionPack)
}

// workaround for https://youtrack.jetbrains.com/issue/KT-48273
/*
afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}*/
