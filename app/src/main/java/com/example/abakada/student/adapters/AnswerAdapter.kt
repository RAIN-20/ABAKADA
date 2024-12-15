package com.example.abakada.student.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.R
import com.example.abakada.teacher.tabs.quizzes.QuizQuestion

class AnswerAdapter(private val questions: List<QuizQuestion>) :
    RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    inner class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.answer_item, parent, false) // Create your answer_item layout
        return AnswerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val question = questions[position]
        holder.textView.text = question.answer
    }

    override fun getItemCount(): Int = questions.size
}