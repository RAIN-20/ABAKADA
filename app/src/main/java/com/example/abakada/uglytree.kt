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

class uglytree : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView
    private lateinit var story3: TextView
    private lateinit var story4: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uglytree)

        // Initialize TextViews
        story1 = findViewById(R.id.part1)
        story2 = findViewById(R.id.part2)
        story3 = findViewById(R.id.part3)
        story4 = findViewById(R.id.part4)


        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        story1.setOnClickListener {
            val text = "Long back, there was a beautiful forest full of dense and green trees. All these trees were huge and straight and had beautiful branches. " +
                    "But, out of all these trees, there was one tree with crooked branches. It was different from the other trees and was even shorter in height. " +
                    "It hated itself because of how ugly and bent it was."
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text =  "All the other trees in the forest made fun of the bent tree and mocked it by calling it an ugly tree. " +
                    "He felt ashamed to be this ugly and envied all the other trees in the forest. He sighed and said, 'Oh! Look at those tall trees. " +
                    "I wish I was as beautiful as them. I hate my hunched and crooked branches.'"
            speakAndHighlightText(text, story2)
        }

        story3.setOnClickListener {
            val text = "One day, a woodcutter came to the forest to cut some trees. He came across the ugly tree and thought to himself, " +
                    "'Look at this crooked and bent tree. There is no use in cutting this tree, it will be of no help to me. I would better cut down all the tall trees.'"
            speakAndHighlightText(text, story3)
        }

        story4.setOnClickListener {
            val text = "Soon all the trees were cut down by the woodcutter. He left the forest, sparing the crooked ugly tree. " +
                    "The tree looked around and saw all of the fellow trees were cut down completely. It now understood the value of his crooked and twisted branches. " +
                    "It thanked God for making it a twisted tree and also how it was saved from being cut down because of its crooked branches."
            speakAndHighlightText(text, story4)
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