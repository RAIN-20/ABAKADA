package com.example.abakada.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.HomeScreen
import com.example.abakada.R
import com.example.abakada.databinding.ActivityLoginBinding
import com.example.abakada.teacher.TeacherDashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        binding.signUpButtonNav.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginButton.setOnClickListener {
            loginUser()
        }

    }
    private fun loginUser() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid

                    // Get user role from Firestore
                    val db = FirebaseFirestore.getInstance()
                    val userRef = db.collection("users").document(uid!!)

                    userRef.get().addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val role = document.getString("role")

                            // Navigate to the appropriate screen based on role
                            val intent = if (role == "Teacher") {
                                Intent(this, TeacherDashboardActivity::class.java)
                            } else {
                                Intent(this, HomeScreen::class.java)
                            }
                            startActivity(intent)
                            finish()
                        } else {
                            // Handle case where user data is not found
                            Toast.makeText(baseContext, "User data not found.", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        // Handle errors in fetching user data
                        Toast.makeText(baseContext, "Error fetching user data.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle login failure
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}