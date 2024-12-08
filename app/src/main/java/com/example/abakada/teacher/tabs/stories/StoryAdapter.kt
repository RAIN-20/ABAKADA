package com.example.abakada.teacher.tabs.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.add
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.R
import com.example.abakada.databinding.StoryItemLayoutBinding
import kotlin.collections.remove

data class StoryData(
    val id: String = "",
    val title: String = "",
)
class StoryAdapter(private val actionModeCallback: ActionMode.Callback) :
    ListAdapter<StoryData, StoryAdapter.StoryViewHolder>(StoryDiffCallback()) {
    val selectedItems = mutableSetOf<StoryData>()
    private var actionMode: ActionMode? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)

        // Set long click listener
        holder.itemView.setOnLongClickListener {
            actionMode = (holder.itemView.context as? AppCompatActivity)?.startActionMode(
                actionModeCallback
            )
            if (story in selectedItems) {
                selectedItems.remove(story)
                holder.itemView.isSelected = false
                // Change background color to default
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
            } else {
                selectedItems.add(story)
                holder.itemView.isSelected = true
                // Change background color to indicate selection (e.g., gray)
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
            }
            // Update action mode title with the number of selected items
            actionMode?.title = selectedItems.size.toString()
            true
        }
        holder.itemView.setOnClickListener {
            if (actionMode != null) { // Only handle clicks in action mode
                if (story in selectedItems) {
                    selectedItems.remove(story)
                    holder.itemView.isSelected = false
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
                } else {
                    selectedItems.add(story)
                    holder.itemView.isSelected = true
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
                }
                // Update action mode title with the number of selected items
                actionMode?.title = selectedItems.size.toString()
            } else {
                // Handle normal item clicks (if needed)
                // ...
            }
        }


        // Set background color based on selection state
        holder.itemView.isSelected = story in selectedItems
        if (holder.itemView.isSelected) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
        }
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