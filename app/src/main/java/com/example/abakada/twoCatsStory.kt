package com.example.abakada

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import java.util.Locale

class twoCatsStory : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_cats_story)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        val story1 = findViewById<TextView>(R.id.part1)
        val story2 = findViewById<TextView>(R.id.part2)
        val story3 = findViewById<TextView>(R.id.part3)



        story1.setOnClickListener {
            speakText("Once upon a time, two cats used to live in a village. They were good friends and both used to live very lovingly with each other.\n" +
                    "\n" +
                    "\n" +
                    "One day both the cats became very hungry while playing. They saw a piece of bread at some distance.  They started fighting over that bread. One cat said,” I found it first so it is mine.” The other cat was saying the same thing.\n" +
                    "\n" +
                    "\n" +
                    "The first cat took two pieces of bread and extended one piece towards the second cat. Seeing this, the other cat said again, \"What is this, you gave me a small piece. That is wrong.\n" +
                    "\n" +
                    "\n" +
                    "A monkey on the tree was seeing them fighting over the bread and wanted to eat that bread too. He said,’’ Why are you quarreling? I can help you because I have a scale which can divide the bread into equal amounts.”\n" +
                    "\n" +
                    "\n" +
                    "Both cats liked the monkeys’ advice. The monkey climbed the tree and brought the scale. He put both the pieces in a pan. He deliberately divided the bread into unequal amounts and said, \"Hey, this piece is big, let's make both equal and after saying this, he ate a little bit from the big piece and ate it.")  // Speak the letter "A"
        }
        story2.setOnClickListener {
            speakText("In this way, every time the scale became heavy, he broke a little bread from that side and started putting it in his mouth. Both the cats were now terrified. She still quietly waited for the monkey\\'s decision as they did not want to give each other more amount of bread.\n" +
                    "\n" +
                    "\n" +
                    "At last small pieces of bread were in pans of the scale. The monkey said, \" As you have seen that I have done the hard work of dividing bread with my scale so I must get the wages of my hard work”. He ate the rest of the pieces of bread. The poor cats went on empty stomachs from there.")  // Speak the letter "A"
        }

        story3.setOnClickListener {
            speakText("Both the cats had realized their mistake and felt that others could take advantage of their weakness.\n" +
                    "\n" +
                    "\n" +
                    "The Moral of the Story\n" +
                    "This story of two cats and a monkey is indeed an amazing story with a very important lesson to learn. After reading these stories about cats, we know that we should always be careful that the third person who is watching the two friends fighting is not helping. On the contrary, he is taking advantage of the fight and he is using the two friends for his own benefit. This story teaches us that “when two people fight, it’s always the third person who gets the benefit”.")  // Speak the letter "A"
        }


    }


    // Function to play a sound
    private fun playSound(soundResId: Int) {
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.start()

        // Release media player resources when the sound finishes playing
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    // Function to speak a text using TextToSpeech
    private fun speakText(text: String) {
        if (::textToSpeech.isInitialized) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        // Release TextToSpeech resources
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }

    }
}