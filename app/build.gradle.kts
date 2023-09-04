plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.gosty.tryoutapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.gosty.tryoutapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        buildConfigField("String", "BASE_URL", "\"https://api.mockfly.dev/mocks/8587320d-4d93-41e7-a2d4-23d4ebc2ceae\"")
        buildConfigField("String", "CLIENT_ID", "\"581018519064-ne12ftklmbnb252ll3bb54tu8o353fek.apps.googleusercontent.com\"")
        buildConfigField("String", "USER_REF", "\"users\"")
        buildConfigField("String", "SCORE_REF", "\"scores\"")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.multidex:multidex:2.0.1")

    // Lottie, Shimmer, and StateView
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.github.Kennyc1012:MultiStateView:2.2.0")

    // Retrofit & Logging
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")

    // Coroutine support
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Circle Image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // ViewPager
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.4.1")
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //circular image view
    implementation ("com.mikhaellopez:circularimageview:4.3.1")
}

kapt {
    correctErrorTypes = true
}