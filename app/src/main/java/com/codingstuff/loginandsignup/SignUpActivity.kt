package com.codingstuff.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.util.Log // Importar Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codingstuff.loginandsignup.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            // Redirigir a SignInActivity cuando se presiona el texto
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    // Intento de crear un usuario
                    Log.d("SignUpActivity", "Attempting to create user with email: $email")
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("SignUpActivity", "User created successfully.")
                            val user = firebaseAuth.currentUser
                            val userId = user?.uid ?: ""

                            // Guardar los datos del usuario en Firestore
                            val userData = hashMapOf(
                                "email" to email,
                                "userId" to userId
                            )

                            firestore.collection("users").document(userId).set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "User registered and data saved!", Toast.LENGTH_SHORT).show()

                                    // Agregar el log aquí
                                    Log.d("SignUpActivity", "User created, redirecting to SignInActivity")

                                    // Redirigir a SignInActivity después de crear la cuenta
                                    val intent = Intent(this, SignInActivity::class.java)
                                    startActivity(intent) // Cambia a SignInActivity
                                    finish() // Finaliza SignUpActivity para que no se pueda volver a ella
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            // Si no se pudo crear el usuario, muestra el mensaje de error
                            Log.d("SignUpActivity", "Failed to create user: ${task.exception?.message}")
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.d("SignUpActivity", "Passwords do not match.")
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("SignUpActivity", "Empty fields detected.")
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
