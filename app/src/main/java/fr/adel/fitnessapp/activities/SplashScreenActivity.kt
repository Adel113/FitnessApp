package fr.adel.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fr.adel.fitnessapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        // Configurer les marges pour les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ajouter un délai avant de lancer HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Créer et lancer l'Intent
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish() // Facultatif : ferme l'écran splash
        }, 3000) // 3000 ms = 3 secondes
    }
}
