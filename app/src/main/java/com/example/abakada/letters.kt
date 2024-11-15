package com.example.abakada

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class letters : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letters)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        // Find the TextView for letter 'A'
        val letterBox = findViewById<TextView>(R.id.letter_a_box)
        val letterBox1 = findViewById<TextView>(R.id.letter_b_box)
        val letterBox2 = findViewById<TextView>(R.id.letter_c_box)
        val letterBox3 = findViewById<TextView>(R.id.letter_d_box)
        val letterBox4 = findViewById<TextView>(R.id.letter_e_box)
        val letterBox5 = findViewById<TextView>(R.id.letter_f_box)
        val letterBox6 = findViewById<TextView>(R.id.letter_g_box)
        val letterBox7 = findViewById<TextView>(R.id.letter_h_box)
        val letterBox8 = findViewById<TextView>(R.id.letter_i_box)
        val letterBox9 = findViewById<TextView>(R.id.letter_j_box)
        val letterBox10 = findViewById<TextView>(R.id.letter_k_box)
        val letterBox11 = findViewById<TextView>(R.id.letter_l_box)
        val letterBox12 = findViewById<TextView>(R.id.letter_m_box)
        val letterBox13 = findViewById<TextView>(R.id.letter_n_box)
        val letterBox14 = findViewById<TextView>(R.id.letter_o_box)
        val letterBox15 = findViewById<TextView>(R.id.letter_p_box)
        val letterBox16 = findViewById<TextView>(R.id.letter_q_box)
        val letterBox17 = findViewById<TextView>(R.id.letter_r_box)
        val letterBox18 = findViewById<TextView>(R.id.letter_s_box)
        val letterBox19 = findViewById<TextView>(R.id.letter_t_box)
        val letterBox20 = findViewById<TextView>(R.id.letter_u_box)
        val letterBox21 = findViewById<TextView>(R.id.letter_v_box)
        val letterBox22 = findViewById<TextView>(R.id.letter_w_box)
        val letterBox23 = findViewById<TextView>(R.id.letter_x_box)
        val letterBox24 = findViewById<TextView>(R.id.letter_y_box)
        val letterBox25 = findViewById<TextView>(R.id.letter_z_box)

        // Set up click listener to play sound and speak letter
        letterBox.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("A")  // Speak the letter "A"
        }
        // Set up click listener to play sound and speak letter
        letterBox1.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("B")  // Speak the letter "A"
        }
        // Set up click listener to play sound and speak letter
        letterBox2.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("C")  // Speak the letter "A"
        }
        // Set up click listener to play sound and speak letter
        letterBox3.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("D")  // Speak the letter "A"
        }
        // Set up click listener to play sound and speak letter
        letterBox4.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("E")  // Speak the letter "A"
        }
        // Set up click listener to play sound and speak letter
        letterBox5.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("F")  // Speak the letter "A"
        }
        letterBox6.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("G")  // Speak the letter "A"
        }
        letterBox7.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("H")  // Speak the letter "A"
        }
        letterBox8.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("I")  // Speak the letter "A"
        }
        letterBox9.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("J")  // Speak the letter "A"
        }
        letterBox10.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("K")  // Speak the letter "A"
        }
        letterBox11.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("L")  // Speak the letter "A"
        }
        letterBox12.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("M")  // Speak the letter "A"
        }
        letterBox13.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("N")  // Speak the letter "A"
        }
        letterBox14.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("O")  // Speak the letter "A"
        }
        letterBox15.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("P")  // Speak the letter "A"
        }
        letterBox16.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("Q")  // Speak the letter "A"
        }
        letterBox17.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("R")  // Speak the letter "A"
        }
        letterBox18.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("S")  // Speak the letter "A"
        }
        letterBox19.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("T")  // Speak the letter "A"
        }
        letterBox20.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("U")  // Speak the letter "A"
        }
        letterBox21.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("V")  // Speak the letter "A"
        }
        letterBox22.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("W")  // Speak the letter "A"
        }
        letterBox23.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("X")  // Speak the letter "A"
        }
        letterBox24.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("Y")  // Speak the letter "A"
        }
        letterBox25.setOnClickListener {
//            playSound(R.raw.yipee1)  // Play the sound file
            speakText("Z")  // Speak the letter "A"
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
