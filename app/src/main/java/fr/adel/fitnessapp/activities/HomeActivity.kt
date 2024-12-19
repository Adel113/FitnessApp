package fr.adel.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import fr.adel.fitnessapp.R
import fr.adel.fitnessapp.fragments.HomeFragment
import fr.adel.fitnessapp.fragments.ExerciseFragment
import fr.adel.fitnessapp.fragments.FoodFragment
import fr.adel.fitnessapp.fragments.CreateProgramFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var logoutButton: Button
    private lateinit var textuser: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var auth: FirebaseAuth // FirebaseAuth instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Initialize Toolbar without title
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Disable title display in Toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Initialize TextView for username display
        textuser = findViewById(R.id.textuser)

        // Initialize logout button
        logoutButton = findViewById(R.id.logout_button)

        // Display default fragment (Home)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, HomeFragment())
            .commit()

        // Handle navigation bar clicks
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

        // Handle logout button click
        logoutButton.setOnClickListener {
            logoutUser()
        }

        // Check if user is logged in and load their username
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loadUserName(currentUser.uid)
        } else {
            Toast.makeText(this, "Utilisateur non connecté.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserName(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.getString("fullnam")
                    textuser.text = "$name"
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Impossible de charger le nom d'utilisateur.", Toast.LENGTH_SHORT).show()
            }
    }

    // Method to log out the user
    private fun logoutUser() {
        auth.signOut()

        // Redirect user to the login screen after logout
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish() // Close current activity to prevent back navigation
    }
}
