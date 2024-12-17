package com.example.abakada.student

import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.teacher.tabs.modules.ModulePart
import java.util.Locale

class PartsAdapter(private val context: Context, private val parts: List<ModulePart>) {
    private var tts: TextToSpeech? = null
    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            } else {
            }
        }
    }
    fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.part_item_layout, parent, false)

        val part = parts[position]

        val partContainer = view.findViewById<LinearLayout>(R.id.part_container)
        val partImageView = view.findViewById<ImageView>(R.id.part_image)
        val partNameTextView = view.findViewById<TextView>(R.id.part_name)

        partNameTextView.text = part.name
        Glide.with(context)
            .load(part.imageUri.toString())
            .placeholder(R.drawable.placeholder_image)
            .into(partImageView)

        partContainer.setOnClickListener {
            val textToSpeak = part.description
            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "utteranceId")
        }

        return view
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}