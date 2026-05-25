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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}

// Создаем задачу копирования
val syncProtocol by tasks.registering(Copy::class) {
    // Откуда берем файлы (относительно папки app/)
    from("../../src/main/java/org/example/marksmangame/net/protocol")
    // Куда кладем в Android проекте
    into("src/main/java/org/example/marksmangame/net/protocol")
}

// Указываем, что эта задача должна выполняться ДО начала сборки Android
afterEvaluate {
    tasks.named("preBuild") {
        dependsOn(syncProtocol)
    }
}