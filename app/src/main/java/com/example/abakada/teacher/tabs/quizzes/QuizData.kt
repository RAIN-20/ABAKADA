package com.example.abakada.teacher.tabs.quizzes

import android.net.Uri

data class QuizQuestion(
    var questionType: String = "", // Type discriminator
    var question: String = "",
    var answer: String = "",
    var imgUri: String? = null // Store Uri as String
)


data class Quiz(
    var id: String = "",
    var name: String = "",
    var type: String = "",
    var difficulty: String = "",
    var questions: List<QuizQuestion> = emptyList()
)