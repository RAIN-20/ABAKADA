package com.example.abakada.teacher.tabs.modules

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abakada.databinding.ModulesItemLayoutBinding
import com.example.abakada.student.ModuleListDetailsActivity
import com.example.abakada.student.ModuleVideoActivity

class ModuleListAdapter : ListAdapter<ModuleData, ModuleListAdapter.ModuleViewHolder>(ModuleDiffCallback()) {

    class ModuleViewHolder(private val binding: ModulesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(moduleData: ModuleData) {
            binding.moduleName.text = moduleData.name
            Glide.with(binding.root.context)
                .load(moduleData.imageUrl)
                .into(binding.moduleImage)

            binding.cardButton.setOnClickListener {
                if(moduleData.type == "Video"){
                    val intent = Intent(binding.root.context, ModuleVideoActivity::class.java)
                    intent.putExtra("moduleName", moduleData.name)
                    intent.putExtra("moduleId", moduleData.id)
                    intent.putExtra("videoLink", moduleData.video.link)
                    intent.putExtra("videoDescription", moduleData.video.description)
                    intent.putExtra("moduleImg", moduleData.imageUrl.toString())
                    binding.root.context.startActivity(intent)
                }else if(moduleData.type == "List/Visual"){
                    val intent = Intent(binding.root.context, ModuleListDetailsActivity::class.java)
                    intent.putExtra("moduleName", moduleData.name)
                    intent.putExtra("moduleImg", moduleData.imageUrl.toString())
                    intent.putExtra("moduleId", moduleData.id)
                    binding.root.context.startActivity(intent)
                }else{
                    Log.d("ModuleListAdapter", "Invalid module type: ${moduleData.type}")
                }
            }
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
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
        return oldItem == newItem
    }
}