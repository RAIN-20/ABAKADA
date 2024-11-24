package com.example.abakada

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import java.util.Locale

class numbers : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        val numberBox = findViewById<TextView>(R.id.textView1)
        val numberBox1 = findViewById<TextView>(R.id.textView2)
        val numberBox2 = findViewById<TextView>(R.id.textView3)
        val numberBox3 = findViewById<TextView>(R.id.textView4)
        val numberBox4 = findViewById<TextView>(R.id.textView5)
        val numberBox5 = findViewById<TextView>(R.id.textView6)
        val numberBox6 = findViewById<TextView>(R.id.textView7)
        val numberBox7 = findViewById<TextView>(R.id.textView8)
        val numberBox8 = findViewById<TextView>(R.id.textView9)
        val numberBox9 = findViewById<TextView>(R.id.textView10)


        numberBox.setOnClickListener {
            speakText("1")  // Speak the letter "A"
        }
        numberBox1.setOnClickListener {
            speakText("2")  // Speak the letter "A"
        }
        numberBox2.setOnClickListener {
            speakText("3")  // Speak the letter "A"
        }
        numberBox3.setOnClickListener {
            speakText("4")  // Speak the letter "A"
        }
        numberBox4.setOnClickListener {
            speakText("5")  // Speak the letter "A"
        }
        numberBox5.setOnClickListener {
            speakText("6")  // Speak the letter "A"
        }
        numberBox6.setOnClickListener {
            speakText("7")  // Speak the letter "A"
        }
        numberBox7.setOnClickListener {
            speakText("8")  // Speak the letter "A"
        }
        numberBox8.setOnClickListener {
            speakText("9")  // Speak the letter "A"
        }
        numberBox9.setOnClickListener {
            speakText("10")  // Speak the letter "A"
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