package com.example.abakada.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.databinding.StoryItemLayoutBinding

data class StoryBook(
    val title: String
)
class StoryBookAdapter(private val storyBooks: List<StoryBook>) :
    RecyclerView.Adapter<StoryBookAdapter.StoryBookViewHolder>() {

    class StoryBookViewHolder(val binding: StoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryBookViewHolder {
        val binding = StoryItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoryBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryBookViewHolder, position: Int) {
        val storyBook = storyBooks[position]
        holder.binding.storyTitle.text = storyBook.title
    }

    override fun getItemCount(): Int = storyBooks.size
}