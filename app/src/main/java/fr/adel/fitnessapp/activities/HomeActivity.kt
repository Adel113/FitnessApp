package fr.adel.fitnessapp.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.fragments.HomeFragment
import fr.adel.fitnessapp.fragments.ExerciseFragment
import fr.adel.fitnessapp.fragments.FoodFragment
import fr.adel.fitnessapp.fragments.CreateProgramFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialiser le Toolbar sans texte
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Désactiver l'affichage du titre dans le Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Afficher le fragment par défaut (Accueil)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, HomeFragment())
            .commit()

        // Gestion des clics sur la barre de navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_progress -> ExerciseFragment()
                R.id.nav_program -> FoodFragment()
                R.id.nav_create_program -> CreateProgramFragment()
                else -> HomeFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, selectedFragment)
                .commit()
            true
        }

        // Gestion du bouton de déconnexion dans le Toolbar
        val logoutButton: ImageButton = findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    // Méthode pour déconnecter l'utilisateur
    private fun logoutUser() {
        // Implémenter la logique de déconnexion ici (par exemple, effacer les préférences, rediriger vers l'écran de connexion, etc.)
    }
}
