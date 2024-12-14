package com.example.abakada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abakada.databinding.ActivityQuizzesBinding
import com.example.abakada.student.QuizActivity

class QuizzesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizzesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizzesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEasy.setOnClickListener { startQuizActivity("Easy") }
        binding.buttonMedium.setOnClickListener { startQuizActivity("Medium") }
        binding.buttonHard.setOnClickListener { startQuizActivity("Hard") }
    }
    private fun startQuizActivity(difficulty: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }
}