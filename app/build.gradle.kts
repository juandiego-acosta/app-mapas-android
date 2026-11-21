plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // El plugin de secrets no es necesario si pones la clave en el Manifest.
}

android {
    namespace = "com.example.appmapas"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.appmapas"
        minSdk = 36
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Dependencias base (las que ya tenías)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.fragment:fragment-ktx:1.7.1") // <-- ESTA ES LA CORRECCIÓN// Añadido para 'FragmentActivity'

    // Dependencias de Mapas (de la P7)
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")

    // --- AÑADIDO: Dependencias para Práctica 8 (Rutas) ---

    // 1. Para Corutinas (manejar tareas en segundo plano como llamadas a API)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // 2. Para Retrofit (hacer llamadas a la API de Directions)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // 3. Para Gson (convertir la respuesta JSON de la API a objetos Kotlin)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 4. Para Google Maps Utils (decodificar la polilínea)
    implementation("com.google.maps.android:android-maps-utils:2.2.0")


    // Dependencias de Pruebas (las que ya tenías)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}