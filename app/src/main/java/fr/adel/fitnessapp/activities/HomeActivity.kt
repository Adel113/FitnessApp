package fr.adel.fitnessapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.adel.fitnessapp.Fragments.HomeFragment
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.fragments.CreateProgramFragment
import fr.adel.fitnessapp.fragments.ProgramFragment
import fr.adel.fitnessapp.fragments.ProgressFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Référence à la barre de navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Charger le fragment par défaut (Accueil)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, HomeFragment())
            .commit()

        // Gestion des clics sur la barre de navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_progress -> ProgressFragment()
                R.id.nav_program -> ProgramFragment()
                R.id.nav_create_program -> CreateProgramFragment()
                else -> HomeFragment()
            }

            // Remplacer le fragment dans le conteneur
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, selectedFragment)
                .commit()

            true
        }

    }

}