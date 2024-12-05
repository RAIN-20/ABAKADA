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


class lionAndTheMouse : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView
    private lateinit var story3: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lion_and_the_mouse)

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
            val text = "Years and years ago, once there was a lion sleeping in the forest under a big tree. A mouse, who lived in the forest too, " +
                    "started playing while moving up and down the lion’s body. She also accidentally ran across the lion’s nose once."
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text = "This soon awakened the lion, and he held the little mouse in his paws. The mouse began to tremble as he knew that the lion was angry " +
                    "and could kill him right there. 'Pardon, O King!' pleaded the little mouse. 'Forgive me this time. I shall never repeat it, and I shall never forget your kindness. " +
                    "Who knows, I may be able to give you a good turn one of these days!'"
            speakAndHighlightText(text, story2)
        }

        story3.setOnClickListener {
            val text = "The lion was somehow in a good mood. Hence, he set the mouse free from his paws. Yet he could not be more amused by the idea of how a tiny mouse could ever help the king of the jungle. " +
                    "After a few days, the mouse suddenly heard the roar of the lion. When she went to look at what’s the matter, she found out that the lion was captured and trapped in a net by his prey. " +
                    "Seeing the lion in such a condition, she immediately started gnawing the ropes of the net until it freed the lion. " +
                    "The lion thanked the little mouse. But the mouse was happy that she could finally repay her debt for sparing her life once. " +
                    "Since then the lion and mouse became good friends, and they lived happily ever after."
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