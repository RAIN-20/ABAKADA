package com.example.abakada.teacher.tabs.quizzes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.R

class QuizzesAdapter(private var quizzes: List<Quiz>) : RecyclerView.Adapter<QuizzesAdapter.QuizViewHolder>() {

    class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quizTitleTextView: TextView = itemView.findViewById(R.id.quizTitleTextView)
        val quizDifficultyTextView: TextView = itemView.findViewById(R.id.quizDifficultyTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quizes_item_layout, parent, false)
        return QuizViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val currentQuiz = quizzes[position]
        holder.quizTitleTextView.text = currentQuiz.name
        holder.quizDifficultyTextView.text = currentQuiz.difficulty
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    fun updateQuizzes(newQuizzes: List<Quiz>) {
        quizzes = newQuizzes
        notifyDataSetChanged()
    }
}