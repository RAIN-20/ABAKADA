package com.example.abakada.student

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.R
import com.example.abakada.databinding.ActivityQuizBinding
import com.example.abakada.teacher.tabs.quizzes.Quiz
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var db: FirebaseFirestore
    private val sharedViewModel: SharedQuizViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val difficulty = intent.getStringExtra("difficulty" ) ?: ""

        fetchRandomQuiz(difficulty)
    }
    private fun fetchRandomQuiz(difficulty: String) {
        db.collection("quizzes")
            .whereEqualTo("difficulty", difficulty)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val randomQuizIndex = (0 until querySnapshot.size()).random()
                    val randomQuiz = querySnapshot.documents[randomQuizIndex].toObject(Quiz::class.java)

                    if (randomQuiz?.type == "Matching") {
                        sharedViewModel.quizData = randomQuiz
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.quiz_activity_container, MatchingTypeFragment()) // Replace with your MatchingTypeFragment
                            .commit()
                    } else {
                        sharedViewModel.quizData = randomQuiz
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.quiz_activity_container, FillInTheBlanksFragment()) // Replace with your FillInTheBlanksFragment
                            .commit()
                    }
                } else {
                    // Handle case where no quizzes are found for the difficulty
                    Log.d("QuizActivity", "No quizzes found for difficulty: $difficulty")
                    Toast.makeText(this, "No quizzes found for difficulty: $difficulty", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Handle Firestore query failure
                Log.e("QuizActivity", "Error fetching quiz: ", exception)
                Toast.makeText(this, "Error fetching quiz: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}