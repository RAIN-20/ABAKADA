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


class twoCatsStory : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView
    private lateinit var story3: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_cats_story)

        // Initialize TextViews
        story1 = findViewById(R.id.part1)
        story2 = findViewById(R.id.part2)
        story3 = findViewById(R.id.part3)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        story1.setOnClickListener {
            val text = "Once upon a time, two cats used to live in a village. They were good friends and both used to live very lovingly with each other. " +
                    "One day both the cats became very hungry while playing. They saw a piece of bread at some distance. They started fighting over that bread. " +
                    "One cat said, 'I found it first so it is mine.' The other cat was saying the same thing."
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text = "In this way, every time the scale became heavy, he broke a little bread from that side and started putting it in his mouth. " +
                    "Both the cats were now terrified. She still quietly waited for the monkey's decision as they did not want to give each other more amount of bread. " +
                    "At last small pieces of bread were in pans of the scale. The monkey said, 'As you have seen that I have done the hard work of dividing bread with my scale so I must get the wages of my hard work.' " +
                    "He ate the rest of the pieces of bread. The poor cats went on empty stomachs from there."
            speakAndHighlightText(text, story2)
        }

        story3.setOnClickListener {
            val text = "Both the cats had realized their mistake and felt that others could take advantage of their weakness. " +
                    "The Moral of the Story: This story of two cats and a monkey is indeed an amazing story with a very important lesson to learn. " +
                    "After reading these stories about cats, we know that we should always be careful that the third person who is watching the two friends fighting is not helping. " +
                    "On the contrary, he is taking advantage of the fight and he is using the two friends for his own benefit. This story teaches us that 'when two people fight, it’s always the third person who gets the benefit.'"
            speakAndHighlightText(text, story3)
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