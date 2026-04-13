plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Add KSP for Room
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" // Make sure this matches your Kotlin version
}
dependencies {
    // Standard AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")

    // Jetpack Compose (UI)
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Compose Navigation (To switch between Login and Dashboard)
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Room Database (Local Storage)
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion") // For Coroutines support
    ksp("androidx.room:room-compiler:$roomVersion")

    // ViewModel & Lifecycle (To connect Data to UI)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
}
