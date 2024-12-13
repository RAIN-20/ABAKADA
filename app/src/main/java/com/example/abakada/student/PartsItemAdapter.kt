package com.example.abakada.student

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.teacher.tabs.modules.ModulePart

class PartsAdapter(private val context: Context, private val parts: List<ModulePart>) {

    fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.part_item_layout, parent, false)

        val part = parts[position]

        val partImageView = view.findViewById<ImageView>(R.id.part_image)
        val partNameTextView = view.findViewById<TextView>(R.id.part_name)

        // Set data for the views (e.g., part name, image)
        partNameTextView.text = part.name
        Glide.with(context)
            .load(part.imageUri.toString())
            .placeholder(R.drawable.placeholder_image)
            .into(partImageView)

        return view
    }
}