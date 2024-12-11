plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
}


android {
    namespace = "fr.adel.fitnessapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "fr.adel.fitnessapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    viewBinding{
        enable = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.android.material:material:1.6.1")
    // CardView
    implementation(libs.cardview)
    implementation("com.github.bumptech.glide:glide:4.15.1")

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.google.code.gson:gson:2.10.1")


    // Retrofit pour faire les appels API
    implementation(libs.retrofit)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp pour les interceptors (utile pour ajouter des entêtes)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Gson pour convertir les réponses JSON
    implementation(libs.gson)

    // Material Design
    implementation(libs.material)

}