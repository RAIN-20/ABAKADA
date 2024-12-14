package com.example.abakada.teacher.tabs.quizzes

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.R

class QuizItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val questionInput: EditText = itemView.findViewById(R.id.quiz_question_input)
    val answerInput: EditText = itemView.findViewById(R.id.quiz_answer_input)
    val answerLabel: TextView = itemView.findViewById(R.id.quiz_answer_label)
    val quizImage: ImageView = itemView.findViewById(R.id.quiz_image)
    val removeItemButton: ImageView = itemView.findViewById(R.id.removeItemButton)

}