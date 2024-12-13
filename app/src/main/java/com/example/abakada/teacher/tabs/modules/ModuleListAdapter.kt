package com.example.abakada.teacher.tabs.modules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abakada.databinding.ModulesItemLayoutBinding

class ModuleListAdapter : ListAdapter<ModuleData, ModuleListAdapter.ModuleViewHolder>(ModuleDiffCallback()) {

    class ModuleViewHolder(private val binding: ModulesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(moduleData: ModuleData) {
            binding.moduleName.text = moduleData.name

            Glide.with(binding.root.context)
                .load(moduleData.imageUrl)
                .into(binding.moduleImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ModulesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val moduleData = getItem(position)
        holder.bind(moduleData)
    }
}

class ModuleDiffCallback : DiffUtil.ItemCallback<ModuleData>() {
    override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
        return oldItem == newItem
    }
}