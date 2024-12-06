package com.example.abakada.teacher.tabs.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.databinding.StoryItemLayoutBinding
data class StoryData(
    val id: String = "", // Assuming you have an ID for each story
    val title: String = "",
    // ... other story properties ...
)
class StoryAdapter : ListAdapter<StoryData, StoryAdapter.StoryViewHolder>(StoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class StoryViewHolder(private val binding: StoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: StoryData) {
            binding.storyTitle.text = story.title
            // ... bind other story data to views ...
        }
    }
}

class StoryDiffCallback : DiffUtil.ItemCallback<StoryData>() {
    override fun areItemsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoryData, newItem: StoryData): Boolean {
        return oldItem == newItem
    }
}