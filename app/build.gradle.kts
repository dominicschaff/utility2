plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
    namespace = "dev.schaff.utility"
    compileSdk {
      version = release(36)
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "dev.schaff.utility"
        /*
        Android 16: API Level 36
        Android 15: API Level 35
        Android 14: API Level 34
         */
        minSdk = 35
        //noinspection OldTargetApi
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
//    packagingOptions {
//        resources {
//            excludes += ["META-INF/DEPENDENCIES", "META-INF/LICENSE", "META-INF/LICENSE.txt", "META-INF/license.txt", "META-INF/LICENSE.md", "META-INF/NOTICE", "META-INF/NOTICE.txt", "META-INF/NOTICE.md", "META-INF/notice.txt", "META-INF/ASL2.0", "META-INF/*.kotlin_module"]
//            excludes += ["META-INF/services/io.jeo.data.Driver", "META-INF/LICENSE", "META-INF/NOTICE"]
//        }
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.zxing)
    implementation(libs.journeyapp.zxing)
    implementation(libs.gson)

    implementation(libs.expandable.fab)
    implementation(libs.ion)
    implementation(libs.androidx.activity.ktx)

    // Mapsforge VTM Core
    implementation(libs.mapsforge.vtm)
    implementation(libs.mapsforge.vtm.themes)
    implementation(libs.mapsforge.vtm.android)
    implementation(libs.mapsforge.vtm.android.mvt)
    implementation(libs.mapsforge.vtm.jts)
    implementation(libs.androidsvg)

    implementation(libs.mapsforge.vtm.hillshading)
    implementation(libs.mapsforge.core)
    implementation(libs.mapsforge.map)
    implementation(libs.mapsforge.map.android)

    runtimeOnly("com.github.mapsforge.vtm:vtm-android:0.27.0:natives-armeabi-v7a")
    runtimeOnly("com.github.mapsforge.vtm:vtm-android:0.27.0:natives-arm64-v8a")
    runtimeOnly("com.github.mapsforge.vtm:vtm-android:0.27.0:natives-x86")
    runtimeOnly("com.github.mapsforge.vtm:vtm-android:0.27.0:natives-x86_64")
}
