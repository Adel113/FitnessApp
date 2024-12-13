package fr.adel.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.fragments.HomeFragment
import fr.adel.fitnessapp.fragments.ExerciseFragment
import fr.adel.fitnessapp.fragments.FoodFragment
import fr.adel.fitnessapp.fragments.CreateProgramFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var logoutButton: Button
    private lateinit var auth: FirebaseAuth  // Déclaration de FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialiser FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Initialiser le Toolbar sans texte
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialisation du bouton de déconnexion
        logoutButton = findViewById(R.id.logout_button)

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

        // Gestion du bouton de déconnexion
        logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    // Méthode pour déconnecter l'utilisateur
    private fun logoutUser() {
        auth.signOut()

        // Rediriger l'utilisateur vers l'écran de connexion après la déconnexion
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()  // Ferme l'activité actuelle pour que l'utilisateur ne puisse pas revenir en arrière
    }
}
