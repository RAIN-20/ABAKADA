package com.example.abakada.teacher.tabs.quizzes

import android.net.Uri

sealed class QuizQuestion {
    abstract var question: String
    abstract var answer: String
    abstract var imgUri: Uri? // Changed to Uri?

    data class FillInTheBlanks(
        override var question: String,
        override var answer: String,
        override var imgUri: Uri? = null // Changed to Uri? and default to null
    ) : QuizQuestion()

    data class MatchingType(
        override var question: String,
        override var answer: String,
        override var imgUri: Uri? = null // Changed to Uri? and default to null
    ) : QuizQuestion()
}

data class Quiz(
    var id: String = "",
    var name: String,
    var type: String,
    var difficulty: String,
    var questions: List<QuizQuestion>
)