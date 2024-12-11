package com.example.abakada.teacher.tabs.modules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.databinding.ModulesItemLayoutBinding

class ModuleListAdapter : ListAdapter<ModuleData, ModuleListAdapter.ModulePartViewHolder>(ModulePartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulePartViewHolder {
        val binding = ModulesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModulePartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModulePartViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class ModulePartViewHolder(private val binding: ModulesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: ModuleData) {
            binding.moduleName.text = module.name
        }
    }
}

class ModulePartDiffCallback : DiffUtil.ItemCallback<ModuleData>() {
    override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
        return oldItem == newItem
    }
}