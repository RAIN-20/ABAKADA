package com.example.abakada


import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class clever_fox : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clever_fox)

        // Initialize TextViews
        story1 = findViewById(R.id.part1)
        story2 = findViewById(R.id.part2)


        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        story1.setOnClickListener {
            val text = "Once, a cunning fox befriended a stork and invited her for a feast. When the stork arrived, the fox served soup in shallow bowls, " +
                    "making it impossible for the long-billed stork to drink. The fox mockingly asked if she enjoyed the soup, to which the stork politely replied " +
                    "she had an upset stomach."
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text = "The stork then invited the fox to her home. For dinner, she served soup in tall jars with narrow necks, which the fox couldn’t access. " +
                    "While the stork sipped easily, the fox remained hungry. The fox realized he had been outsmarted, learning an important lesson about treating others fairly."
            speakAndHighlightText(text, story2)
        }









    }

    private fun speakAndHighlightText(text: String, textView: TextView) {
        val words = text.split(" ")
        val spannableString = SpannableString(text)
        textView.text = spannableString

        var currentWordIndex = 0

        textToSpeech.setSpeechRate(1.0f) // Normal speech rate
        textToSpeech.setPitch(1.0f)      // Normal pitch

        // Function to highlight words within a sentence
        fun highlightWords() {
            if (currentWordIndex < words.size) {
                val start = spannableString.toString().indexOf(words[currentWordIndex])
                val end = start + words[currentWordIndex].length

                // Highlight the current word
                spannableString.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                textView.text = spannableString

                currentWordIndex++
                textView.postDelayed({ highlightWords() }, 400) // Highlight next word
            }
        }

        // Speak text fluently with highlights
        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "ttsID"
        )

        highlightWords() // Start highlighting words
    }


    override fun onDestroy() {
        super.onDestroy()
        // Release TextToSpeech resources
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
}