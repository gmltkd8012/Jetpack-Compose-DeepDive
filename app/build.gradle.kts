import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.korino.study.compose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.korino.study.compose"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    composeCompiler {
        enableStrongSkippingMode.set(true)
        enableIntrinsicRemember.set(true)

        reportsDestination.set(layout.buildDirectory.dir("compose_reports"))
        metricsDestination.set(layout.buildDirectory.dir("compose_metrics"))
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// ✅ IR 덤프 설정 (Kotlin 2.x용)
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        // Kotlin 2.x에서는 -Xphases-to-dump (복수형) 사용
        freeCompilerArgs.addAll(
            listOf(
                "-Xphases-to-dump-after=all",
                "-Xdump-directory=${layout.buildDirectory.get().asFile.absolutePath}/compose-ir"
            )
        )
    }

    doFirst {
        val dumpDir = layout.buildDirectory.get().asFile.resolve("compose-ir")
        println("\n${"=".repeat(60)}")
        println("🔥 Kotlin Compilation Starting")
        println("${"=".repeat(60)}")
        println("📁 IR Dump Target: ${dumpDir.absolutePath}")
        println("🔧 Compiler Args: ${compilerOptions.freeCompilerArgs.get()}")
        println("${"=".repeat(60)}\n")
    }

    doLast {
        println("\n${"=".repeat(60)}")
        println("📊 Compilation Complete")
        println("${"=".repeat(60)}")

        val buildDir = layout.buildDirectory.get().asFile

        // IR 확인
        val irDir = buildDir.resolve("compose-ir")
        if (irDir.exists() && irDir.isDirectory) {
            val irFiles = irDir.listFiles() ?: emptyArray()
            println("✅ IR Dump: ${irDir.absolutePath}")
            println("   Files: ${irFiles.size}")
            if (irFiles.isNotEmpty()) {
                println("   Recent:")
                irFiles.sortedByDescending { it.lastModified() }
                    .take(5)
                    .forEach { println("      📄 ${it.name}") }
            }
        } else {
            println("❌ IR Dump: NOT CREATED")
            println("   Expected: ${irDir.absolutePath}")
            println("   Exists: ${irDir.exists()}")
            println("   Is Directory: ${irDir.isDirectory}")
        }

        // Metrics 확인
        val metricsDir = buildDir.resolve("compose_metrics")
        if (metricsDir.exists()) {
            println("\n✅ Compose Metrics: ${metricsDir.absolutePath}")
            metricsDir.listFiles()?.forEach {
                println("   📄 ${it.name} (${it.length() / 1024}KB)")
            }
        }

        // Reports 확인
        val reportsDir = buildDir.resolve("compose_reports")
        if (reportsDir.exists()) {
            println("\n✅ Compose Reports: ${reportsDir.absolutePath}")
            reportsDir.listFiles()?.forEach {
                println("   📄 ${it.name}")
            }
        }

        println("${"=".repeat(60)}\n")
    }
}