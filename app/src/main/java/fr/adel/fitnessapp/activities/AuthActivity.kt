package fr.adel.fitnessapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import fr.adel.fitnessapp.R

class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    lateinit var InputLayoutEmail:TextInputLayout
    lateinit var InputLayoutPassword:TextInputLayout
    lateinit var tvRegister:  TextView
    lateinit var btnConnect: MaterialButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_autentification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        tvRegister = findViewById(R.id.tvRegister)
        InputLayoutEmail = findViewById(R.id.textInputLayoutEmail)
        InputLayoutPassword = findViewById(R.id.textInputLayoutPassword)
        btnConnect = findViewById(R.id.btnConnect)


    }

    override fun onStart() {
        super.onStart()
        tvRegister.setOnClickListener{
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        btnConnect.setOnClickListener{

            InputLayoutPassword.isErrorEnabled = false
            InputLayoutEmail.isErrorEnabled = false

            val email = InputLayoutEmail.editText?.text.toString()
            val password = InputLayoutPassword.editText?.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                if (password.isEmpty()) {
                    InputLayoutPassword.error = "Password is required"
                    InputLayoutPassword.isErrorEnabled = true
                }
                if (email.isEmpty()) {
                    InputLayoutEmail.error = "Email is required"
                    InputLayoutEmail.isErrorEnabled = true

                }
            }else{
                singIn(email, password)
            }


        }
    }

    fun singIn(email: String, password: String){
        Log.d("singIn", "singIn user.....")

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task->
            if (task.isSuccessful){
                Intent(this, HomeActivity::class.java).also {
                    startActivity(it)
                }
                finish()
            }else{
                InputLayoutPassword.error = "failed"
                InputLayoutPassword.isErrorEnabled = true
                InputLayoutEmail.isErrorEnabled = true
            }

        }


    }
}