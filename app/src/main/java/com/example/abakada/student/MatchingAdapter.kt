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

class MatchingAdapter(private val questions: List<QuizQuestion>) :
    RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matching, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        Glide.with(holder.itemView.context)
            .load(question.imgUri)
            .into(holder.imageView)
        holder.textView.text = question.answer
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}