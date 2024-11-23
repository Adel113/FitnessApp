package fr.adel.fitnessapp

import android.app.AppComponentFactory
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class fitnessapp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}