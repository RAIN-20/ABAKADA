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

class snow_white : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snow_white)


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
            val text = "Once upon a time, Princess Snow White lived in a castle with her father, the King, and her stepmother, the Queen. " +
                    "The King often reminded Snow White, “A ruler must always be fair, listening to all sides before deciding.”\n\n" +
                    "The Queen, proud of her fairness, asked her magic mirror, “Mirror, mirror, on the wall, who is the fairest of them all?”\n\n" +
                    "“Snow White is the fairest of them all,” the mirror replied.\n\n" +
                    "Furious, the Queen exclaimed, “Impossible! No one can be fairer than me!”"
            speakAndHighlightText(text, story1)
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text = "Snow White ran as fast as she could through the dark, dense woods. The trees seemed to close in around her, but she kept going, not daring to stop.\n\n" +
                    "After what felt like hours, Snow White stumbled upon a small cottage. She knocked on the door, but there was no answer.\n\n" +
                    "Feeling exhausted, Snow White pushed the door open and entered the cottage. It was small, cozy, and there were seven little beds arranged neatly inside.\n\n" +
                    "Tired from her run, Snow White lay down on one of the beds and fell fast asleep, unaware of the seven dwarfs who lived there."
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