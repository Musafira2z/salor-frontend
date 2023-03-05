object Versions {
    const val koin = "3.2.0"
    const val Ktor = "2.2.2"
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.apollographql.apollo3").version("3.7.4")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//        }
//    }

    js(IR) {
        useCommonJs()
        browser()
    }

    val ballast = "2.3.0"

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.copper-leaf:ballast-core:$ballast")
                implementation("io.github.copper-leaf:ballast-saved-state:$ballast")
                implementation("io.github.copper-leaf:ballast-repository:$ballast")
                implementation("io.github.copper-leaf:ballast-firebase-crashlytics:$ballast")
                implementation("io.github.copper-leaf:ballast-firebase-analytics:$ballast")
                implementation("io.github.copper-leaf:ballast-debugger:$ballast")
                implementation("io.github.copper-leaf:ballast-navigation:$ballast")
                api("io.insert-koin:koin-core:${Versions.koin}")
                implementation("com.apollographql.apollo3:apollo-runtime:3.7.4")
                implementation("io.ktor:ktor-client-logging:${Versions.Ktor}")
//                implementation("com.russhwolf:multiplatform-settings:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-coroutines:1.0.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.github.copper-leaf:ballast-test:$ballast")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:${Versions.Ktor}")
            }
        }
        val androidUnitTest by getting
//        val iosX64Main by getting
//        val iosArm64Main by getting
//        val iosSimulatorArm64Main by getting
//        val iosMain by creating {
//            dependsOn(commonMain)
//            iosX64Main.dependsOn(this)
//            iosArm64Main.dependsOn(this)
//            iosSimulatorArm64Main.dependsOn(this)
//        }
//        val iosX64Test by getting
//        val iosArm64Test by getting
//        val iosSimulatorArm64Test by getting
//        val iosTest by creating {
//            dependsOn(commonTest)
//            iosX64Test.dependsOn(this)
//            iosArm64Test.dependsOn(this)
//            iosSimulatorArm64Test.dependsOn(this)
//        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:${Versions.Ktor}")
            }
        }
        val jsTest by getting
    }
}

android {
    namespace = "com.musafira2z.store"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

apollo {
    packageName.set("com.musafira2z.store")
}