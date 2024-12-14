package com.example.abakada.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.R
import com.example.abakada.databinding.ActivitySignUpBinding
import com.example.abakada.utils.LoadingOverlayUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        binding.signUpButtonNav.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.registerButton.setOnClickListener {
            signUpUser()
        }

    }
    private fun signUpUser() {
        LoadingOverlayUtils.showLoadingOverlay(this)
        val email = binding.emailInput.text.toString()
        val name = binding.nameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confPasswordInput.text.toString()

        val selectedRoleId = binding.roleRadioGroup.checkedRadioButtonId
        val selectedRole = when (selectedRoleId) {
            R.id.patientRadioButton -> "Student"
            R.id.doctorRadioButton -> "Teacher"
            else -> "Student" // Default to Patient if none selected
        }

        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            LoadingOverlayUtils.hideLoadingOverlay(this)
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            LoadingOverlayUtils.hideLoadingOverlay(this)
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    // Store user data in Firestore
                    val userData = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "role" to selectedRole,
                        "created_at" to FieldValue.serverTimestamp()
                    )

                    db.collection("users").document(user!!.uid)
                        .set(userData)
                        .addOnSuccessListener {
                            Log.d(TAG, "User data added to Firestore")
                            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()

                            // Navigate to the main activity
                            val intent = Intent(this, LoginActivity::class.java)
                            LoadingOverlayUtils.hideLoadingOverlay(this)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding user data to Firestore", e)
                            LoadingOverlayUtils.hideLoadingOverlay(this)
                            Toast.makeText(this, "Error creating account", Toast.LENGTH_SHORT).show()
                        }

                } else {

                    LoadingOverlayUtils.hideLoadingOverlay(this)
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}