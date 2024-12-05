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

class jackandbean : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView
    private lateinit var story3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jackandbean)

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
            val text = "There was once upon a time a poor widow who had an only son named Jack, and a cow named Milky-White. " +
                    "All they had to live on was the milk the cow gave every morning, which they carried to the market and sold – until one morning Milky-White gave no milk.\n\n" +
                    "“What shall we do, what shall we do?” said the widow, wringing her hands.\n\n" +
                    "“Cheer up mother, I’ll go and get work somewhere,” said Jack."
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text = "We’ve tried that before, and nobody would take you,” said his mother. “We must sell Milky-White and with the money, start a shop or something.”\n\n" +
                    "“Alright, mother,” said Jack. “It’s market day today, and I’ll soon sell Milky-White, and then we’ll see what we can do.”\n\n" +
                    "So he took the cow, and off he started. He hadn’t gone far when he met a funny looking old man, who said to him, “Good morning, Jack.”\n\n" +
                    "“Good morning to you,” said Jack, and wondered how he knew his name.\n\n" +
                    "“Well Jack, where are you off to?” said the man.\n\n" +
                    "“I’m going to market to sell our cow there.”\n\n" +
                    "“Oh, you look the proper sort of chap to sell cows,” said the man. “I wonder if you know how many beans make five.”\n\n" +
                    "“Two in each hand and one in your mouth,” said Jack, as sharp as a needle.\n\n" +
                    "“Right you are,” says the man, “and here they are, the very beans themselves,” he went on, pulling out of his pocket a number of strange looking beans. " +
                    "“As you are so sharp,” said he, “I don’t mind doing a swap with you — your cow for these beans.”\n\n" +
                    "“Go along,” said Jack. “You take me for a fool!”\n\n" +
                    "“Ah! You don’t know what these beans are,” said the man. “If you plant them overnight, by morning they grow right up to the sky.”\n\n" +
                    "“Really?” said Jack. “You don’t say so.”"
            speakAndHighlightText(text, story2)
        }

        story3.setOnClickListener {
            val text = "Jack and his mother were poor, so she sent him to sell their cow. Instead, Jack traded it for magical beans. " +
                    "His mother was furious and tossed the beans out the window. By morning, a giant beanstalk had grown into the sky.\n\n" +
                    "Jack climbed it and found a castle where an ogre lived. He snuck inside, stole a bag of gold, and escaped. Later, he climbed back, stole a hen that laid golden eggs, and escaped again. " +
                    "On his third trip, he took a magical harp that cried out, waking the ogre, who chased him down the beanstalk.\n\n" +
                    "Jack chopped it down, the ogre fell, and Jack and his mother lived happily ever after with their newfound riches."
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