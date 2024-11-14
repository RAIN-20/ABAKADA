package com.example.abakada

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class letters : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letters)

        // Find the TextView for letter 'A'
        val letterBox = findViewById<TextView>(R.id.letter_a_box)
        val letterBox1 = findViewById<TextView>(R.id.letter_b_box)
        val letterBox2 = findViewById<TextView>(R.id.letter_c_box)
        val letterBox3 = findViewById<TextView>(R.id.letter_d_box)
        val letterBox4 = findViewById<TextView>(R.id.letter_e_box)
        val letterBox5 = findViewById<TextView>(R.id.letter_f_box)
//        val letterBox6 = findViewById<TextView>(R.id.letter_g_box)
//        val letterBox7 = findViewById<TextView>(R.id.letter_h_box)
//        val letterBox8 = findViewById<TextView>(R.id.letter_i_box)
//        val letterBox9 = findViewById<TextView>(R.id.letter_j_box)
//        val letterBox10 = findViewById<TextView>(R.id.letter_k_box)
//        val letterBox11 = findViewById<TextView>(R.id.letter_l_box)
//        val letterBox12 = findViewById<TextView>(R.id.letter_m_box)
//        val letterBox13 = findViewById<TextView>(R.id.letter_n_box)
//        val letterBox14 = findViewById<TextView>(R.id.letter_o_box)
//        val letterBox15 = findViewById<TextView>(R.id.letter_p_box)
//        val letterBox16 = findViewById<TextView>(R.id.letter_q_box)
//        val letterBox17 = findViewById<TextView>(R.id.letter_r_box)
//        val letterBox18 = findViewById<TextView>(R.id.letter_s_box)
//        val letterBox19 = findViewById<TextView>(R.id.letter_t_box)
//        val letterBox20 = findViewById<TextView>(R.id.letter_u_box)
//        val letterBox21 = findViewById<TextView>(R.id.letter_v_box)
//        val letterBox22 = findViewById<TextView>(R.id.letter_w_box)
//        val letterBox23 = findViewById<TextView>(R.id.letter_x_box)
//        val letterBox24 = findViewById<TextView>(R.id.letter_y_box)
//        val letterBox25 = findViewById<TextView>(R.id.letter_z_box)

        // Set up click listener to play sound
        letterBox.setOnClickListener {
            playSound(R.raw.yipee1)  // Replace with your sound file's name
        }
        letterBox1.setOnClickListener {
            playSound(R.raw.yipee1)  // Replace with your sound file's name
        }
        letterBox2.setOnClickListener {
            playSound(R.raw.yipee1)  // Replace with your sound file's name
        }
        letterBox3.setOnClickListener {
            playSound(R.raw.yipee1)  // Replace with your sound file's name
        }
        letterBox4.setOnClickListener {
            playSound(R.raw.yipee1)  // Replace with your sound file's name
        }
        letterBox5.setOnClickListener {
            playSound(R.raw.yipee1)  // Replace with your sound file's name
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

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}
