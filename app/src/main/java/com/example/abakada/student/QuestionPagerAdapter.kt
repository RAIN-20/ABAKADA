package com.example.abakada.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.teacher.tabs.quizzes.QuizQuestion

class QuestionPagerAdapter(private val questions: List<QuizQuestion>) :
    RecyclerView.Adapter<QuestionPagerAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val questionImageView: ImageView = itemView.findViewById(R.id.questionImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false) // Create your item layout
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]

        // Set question text
        holder.questionTextView.text = question.question

        // Set image (if available)
        if (!question.imgUri.isNullOrEmpty()) {
            holder.questionImageView.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(question.imgUri)
                .into(holder.questionImageView)
        } else {
            holder.questionImageView.visibility = View.GONE
        }

        // ... (handle answer input) ...
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}