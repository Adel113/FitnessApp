package fr.adel.fitnessapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRegistrar
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
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
        // Configurer la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Activer le bouton retour
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        auth = Firebase.auth
        layoutTextInputConfirmPassword = findViewById(R.id.layoutTextInputConfirmPassword)
        layoutTextInputEmail = findViewById(R.id.layoutTextInputEmail)
        layoutTextInputPassword = findViewById(R.id.layoutTextInputPassword)
        layoutTextInputName = findViewById(R.id.layoutTextInputName)
        btnRegister = findViewById(R.id.btnRegister)


        btnRegister.setOnClickListener{

            initErrors()

            var email = layoutTextInputEmail.editText?.text.toString()
            var password = layoutTextInputPassword.editText?.text.toString()
            var confirmPassword = layoutTextInputConfirmPassword.editText?.text.toString()
            var name = layoutTextInputName.editText?.text.toString()

            if (email.isEmpty() || name.isEmpty() || confirmPassword.isEmpty() || password.isEmpty()){

                if (name.isEmpty()) {
                    layoutTextInputName.error = "name is required"
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
                    layoutTextInputConfirmPassword.error = "confirmPassword is required"
                    layoutTextInputConfirmPassword.isErrorEnabled = true

                }
            }else{
                if (password != confirmPassword){
                    layoutTextInputConfirmPassword.error = "Passwords did not match"
                    layoutTextInputConfirmPassword.isErrorEnabled = true
                }else {
                    // creation d'un use dans le module auth de firebase
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task->
                        if(task.isSuccessful){
                            val user = hashMapOf(
                                "fullnam" to name,
                                "email" to email,
                            )

                            val currentUser = auth.currentUser
                            // creation d'un use dans le module auth de firestore
                            val db = Firebase.firestore
                            db.collection("users").document(currentUser!!.uid).set(user).addOnSuccessListener {
                                Intent(this, HomeActivity::class.java).also{
                                    startActivity(it)
                                }
                            }.addOnFailureListener{
                                layoutTextInputConfirmPassword.error = "try again"
                                layoutTextInputConfirmPassword.isErrorEnabled = true
                            }


                        }else{
                            layoutTextInputConfirmPassword.error = "try again"
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

    // Gérer les clics sur les éléments de menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Retour en arrière
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
