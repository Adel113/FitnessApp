plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Ajoutez le plugin Google Services ici
    id("com.google.gms.google-services") version "4.4.2" apply false
}
