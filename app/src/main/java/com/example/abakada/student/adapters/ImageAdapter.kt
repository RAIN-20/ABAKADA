package com.example.abakada.student.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.teacher.tabs.quizzes.QuizQuestion

class ImageAdapter(private val questions: List<QuizQuestion>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false) // Create your image_item layout
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val question = questions[position]
        Glide.with(holder.itemView.context)
            .load(question.imgUri)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = questions.size
}