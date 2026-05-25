plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.marksmanobserver"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.marksmanobserver"
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
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}

// Создаем задачу копирования
val syncProtocol by tasks.registering(Copy::class) {
    // Берем за основу корень исходников сервера
    from("../../src/main/java")

    // Указываем, какие именно файлы и папки нам нужны:
    include("org/example/marksmangame/net/protocol/**") // Весь протокол
    include("org/example/marksmangame/model/GameBounds.java") // WA

    // Кладем с сохранением структуры в корень исходников Android
    into("src/main/java")
}

afterEvaluate {
    tasks.named("preBuild") {
        dependsOn(syncProtocol)
    }
}