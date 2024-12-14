package com.example.abakada.student

import androidx.lifecycle.ViewModel
import com.example.abakada.teacher.tabs.quizzes.Quiz

class SharedQuizViewModel : ViewModel() {
    var quizData: Quiz? = null
}