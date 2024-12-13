package fr.adel.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import fr.adel.fitnessapp.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var layoutTextInputName: TextInputLayout
    lateinit var layoutTextInputEmail: TextInputLayout
    lateinit var layoutTextInputPassword: TextInputLayout
    lateinit var layoutTextInputConfirmPassword: TextInputLayout
    lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Configurer les marges pour tenir compte des barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Initialiser la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)

        // Bouton retour personnalisé
        val retourButton: ImageButton = findViewById(R.id.retour_button)
        retourButton.setOnClickListener {
            onBackPressed() // Retour à l'activité précédente
        }

        // Initialisation de l'authentification Firebase
        auth = FirebaseAuth.getInstance()

        // Initialisation des champs
        layoutTextInputConfirmPassword = findViewById(R.id.layoutTextInputConfirmPassword)
        layoutTextInputEmail = findViewById(R.id.layoutTextInputEmail)
        layoutTextInputPassword = findViewById(R.id.layoutTextInputPassword)
        layoutTextInputName = findViewById(R.id.layoutTextInputName)
        btnRegister = findViewById(R.id.btnRegister)


        // Gérer l'action du bouton d'inscription
        btnRegister.setOnClickListener {
            initErrors()

            val email = layoutTextInputEmail.editText?.text.toString()
            val password = layoutTextInputPassword.editText?.text.toString()
            val confirmPassword = layoutTextInputConfirmPassword.editText?.text.toString()
            val name = layoutTextInputName.editText?.text.toString()

            if (email.isEmpty() || name.isEmpty() || confirmPassword.isEmpty() || password.isEmpty()) {
                if (name.isEmpty()) {
                    layoutTextInputName.error = "Name is required"
                    layoutTextInputName.isErrorEnabled = true
                }
                if (email.isEmpty()) {
                    layoutTextInputEmail.error = "Email is required"
                    layoutTextInputEmail.isErrorEnabled = true
                }
                if (password.isEmpty()) {
                    layoutTextInputPassword.error = "Password is required"
                    layoutTextInputPassword.isErrorEnabled = true
                }
                if (confirmPassword.isEmpty()) {
                    layoutTextInputConfirmPassword.error = "Confirm password is required"
                    layoutTextInputConfirmPassword.isErrorEnabled = true
                }
            } else {
                if (password != confirmPassword) {
                    layoutTextInputConfirmPassword.error = "Passwords do not match"
                    layoutTextInputConfirmPassword.isErrorEnabled = true
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = hashMapOf("fullnam" to name, "email" to email)
                            val currentUser = auth.currentUser
                            val db = FirebaseFirestore.getInstance()
                            db.collection("users").document(currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    Intent(this, HomeActivity::class.java).also {
                                        startActivity(it)
                                    }
                                }
                                .addOnFailureListener {
                                    layoutTextInputConfirmPassword.error = "Try again"
                                    layoutTextInputConfirmPassword.isErrorEnabled = true
                                }
                        } else {
                            layoutTextInputConfirmPassword.error = "Try again"
                            layoutTextInputConfirmPassword.isErrorEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun initErrors() {
        layoutTextInputPassword.isErrorEnabled = false
        layoutTextInputEmail.isErrorEnabled = false
        layoutTextInputName.isErrorEnabled = false
        layoutTextInputConfirmPassword.isErrorEnabled = false
    }

}
