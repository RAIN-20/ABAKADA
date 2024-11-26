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

        val numberBox1 = findViewById<TextView>(R.id.textView1)
        val numberBox2 = findViewById<TextView>(R.id.textView2)
        val numberBox3 = findViewById<TextView>(R.id.textView3)
        val numberBox4 = findViewById<TextView>(R.id.textView4)
        val numberBox5 = findViewById<TextView>(R.id.textView5)
        val numberBox6 = findViewById<TextView>(R.id.textView6)
        val numberBox7 = findViewById<TextView>(R.id.textView7)
        val numberBox8 = findViewById<TextView>(R.id.textView8)
        val numberBox9 = findViewById<TextView>(R.id.textView9)
        val numberBox10 = findViewById<TextView>(R.id.textView10)
        val numberBox11 = findViewById<TextView>(R.id.textView11)
        val numberBox12 = findViewById<TextView>(R.id.textView12)
        val numberBox13 = findViewById<TextView>(R.id.textView13)
        val numberBox14 = findViewById<TextView>(R.id.textView14)
        val numberBox15 = findViewById<TextView>(R.id.textView15)
        val numberBox16 = findViewById<TextView>(R.id.textView16)
        val numberBox17 = findViewById<TextView>(R.id.textView17)
        val numberBox18 = findViewById<TextView>(R.id.textView18)
        val numberBox19 = findViewById<TextView>(R.id.textView19)
        val numberBox20 = findViewById<TextView>(R.id.textView20)
        val numberBox21 = findViewById<TextView>(R.id.textView21)
        val numberBox22 = findViewById<TextView>(R.id.textView22)
        val numberBox23 = findViewById<TextView>(R.id.textView23)
        val numberBox24 = findViewById<TextView>(R.id.textView24)
        val numberBox25 = findViewById<TextView>(R.id.textView25)
        val numberBox26 = findViewById<TextView>(R.id.textView26)
        val numberBox27 = findViewById<TextView>(R.id.textView27)
        val numberBox28 = findViewById<TextView>(R.id.textView28)
        val numberBox29 = findViewById<TextView>(R.id.textView29)
        val numberBox30 = findViewById<TextView>(R.id.textView30)
        val numberBox31 = findViewById<TextView>(R.id.textView31)
        val numberBox32 = findViewById<TextView>(R.id.textView32)
        val numberBox33 = findViewById<TextView>(R.id.textView33)
        val numberBox34 = findViewById<TextView>(R.id.textView34)
        val numberBox35 = findViewById<TextView>(R.id.textView35)
        val numberBox36 = findViewById<TextView>(R.id.textView36)
        val numberBox37 = findViewById<TextView>(R.id.textView37)
        val numberBox38 = findViewById<TextView>(R.id.textView38)
        val numberBox39 = findViewById<TextView>(R.id.textView39)
        val numberBox40 = findViewById<TextView>(R.id.textView40)
        val numberBox41 = findViewById<TextView>(R.id.textView41)
        val numberBox42 = findViewById<TextView>(R.id.textView42)
        val numberBox43 = findViewById<TextView>(R.id.textView43)
        val numberBox44 = findViewById<TextView>(R.id.textView44)
        val numberBox45 = findViewById<TextView>(R.id.textView45)
        val numberBox46 = findViewById<TextView>(R.id.textView46)
        val numberBox47 = findViewById<TextView>(R.id.textView47)
        val numberBox48 = findViewById<TextView>(R.id.textView48)
        val numberBox49 = findViewById<TextView>(R.id.textView49)
        val numberBox50 = findViewById<TextView>(R.id.textView50)
        val numberBox51 = findViewById<TextView>(R.id.textView51)
        val numberBox52 = findViewById<TextView>(R.id.textView52)
        val numberBox53 = findViewById<TextView>(R.id.textView53)
        val numberBox54 = findViewById<TextView>(R.id.textView54)
        val numberBox55 = findViewById<TextView>(R.id.textView55)
        val numberBox56 = findViewById<TextView>(R.id.textView56)
        val numberBox57 = findViewById<TextView>(R.id.textView57)
        val numberBox58 = findViewById<TextView>(R.id.textView58)
        val numberBox59 = findViewById<TextView>(R.id.textView59)
        val numberBox60 = findViewById<TextView>(R.id.textView60)
        val numberBox61 = findViewById<TextView>(R.id.textView61)
        val numberBox62 = findViewById<TextView>(R.id.textView62)
        val numberBox63 = findViewById<TextView>(R.id.textView63)
        val numberBox64 = findViewById<TextView>(R.id.textView64)
        val numberBox65 = findViewById<TextView>(R.id.textView65)
        val numberBox66 = findViewById<TextView>(R.id.textView66)
        val numberBox67 = findViewById<TextView>(R.id.textView67)
        val numberBox68 = findViewById<TextView>(R.id.textView68)
        val numberBox69 = findViewById<TextView>(R.id.textView69)
        val numberBox70 = findViewById<TextView>(R.id.textView70)
        val numberBox71 = findViewById<TextView>(R.id.textView71)
        val numberBox72 = findViewById<TextView>(R.id.textView72)
        val numberBox73 = findViewById<TextView>(R.id.textView72)
        val numberBox74 = findViewById<TextView>(R.id.textView74)
        val numberBox75 = findViewById<TextView>(R.id.textView75)
        val numberBox76 = findViewById<TextView>(R.id.textView76)
        val numberBox77 = findViewById<TextView>(R.id.textView77)
        val numberBox78 = findViewById<TextView>(R.id.textView78)
        val numberBox79 = findViewById<TextView>(R.id.textView79)
        val numberBox80 = findViewById<TextView>(R.id.textView80)
        val numberBox81 = findViewById<TextView>(R.id.textView81)
        val numberBox82 = findViewById<TextView>(R.id.textView82)
        val numberBox83 = findViewById<TextView>(R.id.textView83)
        val numberBox84 = findViewById<TextView>(R.id.textView84)
        val numberBox85 = findViewById<TextView>(R.id.textView85)
        val numberBox86 = findViewById<TextView>(R.id.textView86)
        val numberBox87 = findViewById<TextView>(R.id.textView87)
        val numberBox88 = findViewById<TextView>(R.id.textView88)
        val numberBox89 = findViewById<TextView>(R.id.textView89)
        val numberBox90 = findViewById<TextView>(R.id.textView90)
        val numberBox91 = findViewById<TextView>(R.id.textView91)
        val numberBox92 = findViewById<TextView>(R.id.textView92)
        val numberBox93 = findViewById<TextView>(R.id.textView93)
        val numberBox94 = findViewById<TextView>(R.id.textView94)
        val numberBox95 = findViewById<TextView>(R.id.textView95)
        val numberBox96 = findViewById<TextView>(R.id.textView96)
        val numberBox97 = findViewById<TextView>(R.id.textView97)
        val numberBox98 = findViewById<TextView>(R.id.textView98)
        val numberBox99 = findViewById<TextView>(R.id.textView99)
        val numberBox100 = findViewById<TextView>(R.id.textView100)


        numberBox1.setOnClickListener {
            speakText("1")  // Speak the letter "A"
        }
        numberBox2.setOnClickListener {
            speakText("2")  // Speak the letter "A"
        }
        numberBox3.setOnClickListener {
            speakText("3")  // Speak the letter "A"
        }
        numberBox4.setOnClickListener {
            speakText("4")  // Speak the letter "A"
        }
        numberBox5.setOnClickListener {
            speakText("5")  // Speak the letter "A"
        }
        numberBox6.setOnClickListener {
            speakText("6")  // Speak the letter "A"
        }
        numberBox7.setOnClickListener {
            speakText("7")  // Speak the letter "A"
        }
        numberBox8.setOnClickListener {
            speakText("8")  // Speak the letter "A"
        }
        numberBox9.setOnClickListener {
            speakText("9")  // Speak the letter "A"
        }
        numberBox10.setOnClickListener {
            speakText("10")  // Speak the letter "A"
        }
        numberBox11.setOnClickListener {
            speakText("11")  // Speak the letter "A"
        }
        numberBox12.setOnClickListener {
            speakText("12")  // Speak the letter "A"
        }
        numberBox13.setOnClickListener {
            speakText("13")  // Speak the letter "A"
        }
        numberBox14.setOnClickListener {
            speakText("14")  // Speak the letter "A"
        }
        numberBox15.setOnClickListener {
            speakText("15")  // Speak the letter "A"
        }
        numberBox16.setOnClickListener {
            speakText("16")  // Speak the letter "A"
        }
        numberBox17.setOnClickListener {
            speakText("17")  // Speak the letter "A"
        }
        numberBox18.setOnClickListener {
            speakText("18")  // Speak the letter "A"
        }
        numberBox19.setOnClickListener {
            speakText("19")  // Speak the letter "A"
        }
        numberBox20.setOnClickListener {
            speakText("20")  // Speak the letter "A"
        }
        numberBox21.setOnClickListener {
            speakText("21")  // Speak the letter "A"
        }
        numberBox22.setOnClickListener {
            speakText("22")  // Speak the letter "A"
        }
        numberBox23.setOnClickListener {
            speakText("23")  // Speak the letter "A"
        }
        numberBox24.setOnClickListener {
            speakText("24")  // Speak the letter "A"
        }
        numberBox25.setOnClickListener {
            speakText("25")  // Speak the letter "A"
        }
        numberBox26.setOnClickListener {
            speakText("26")  // Speak the letter "A"
        }
        numberBox27.setOnClickListener {
            speakText("27")  // Speak the letter "A"
        }
        numberBox28.setOnClickListener {
            speakText("28")  // Speak the letter "A"
        }
        numberBox29.setOnClickListener {
            speakText("29")  // Speak the letter "A"
        }
        numberBox30.setOnClickListener {
            speakText("30")  // Speak the letter "A"
        }
        numberBox31.setOnClickListener {
            speakText("31")  // Speak the letter "A"
        }
        numberBox32.setOnClickListener {
            speakText("32")  // Speak the letter "A"
        }
        numberBox33.setOnClickListener {
            speakText("33")  // Speak the letter "A"
        }
        numberBox34.setOnClickListener {
            speakText("34")  // Speak the letter "A"
        }
        numberBox35.setOnClickListener {
            speakText("35")  // Speak the letter "A"
        }
        numberBox36.setOnClickListener {
            speakText("36")  // Speak the letter "A"
        }
        numberBox37.setOnClickListener {
            speakText("37")  // Speak the letter "A"
        }
        numberBox38.setOnClickListener {
            speakText("38")  // Speak the letter "A"
        }
        numberBox39.setOnClickListener {
            speakText("39")  // Speak the letter "A"
        }
        numberBox40.setOnClickListener {
            speakText("40")  // Speak the letter "A"
        }
        numberBox41.setOnClickListener {
            speakText("41")  // Speak the letter "A"
        }
        numberBox42.setOnClickListener {
            speakText("42")  // Speak the letter "A"
        }
        numberBox43.setOnClickListener {
            speakText("43")  // Speak the letter "A"
        }
        numberBox44.setOnClickListener {
            speakText("44")  // Speak the letter "A"
        }
        numberBox45.setOnClickListener {
            speakText("45")  // Speak the letter "A"
        }
        numberBox46.setOnClickListener {
            speakText("46")  // Speak the letter "A"
        }
        numberBox47.setOnClickListener {
            speakText("47")  // Speak the letter "A"
        }
        numberBox48.setOnClickListener {
            speakText("48")  // Speak the letter "A"
        }
        numberBox49.setOnClickListener {
            speakText("49")  // Speak the letter "A"
        }
        numberBox50.setOnClickListener {
            speakText("50")  // Speak the letter "A"
        }
        numberBox51.setOnClickListener {
            speakText("51")  // Speak the letter "A"
        }
        numberBox52.setOnClickListener {
            speakText("52")  // Speak the letter "A"
        }
        numberBox53.setOnClickListener {
            speakText("53")  // Speak the letter "A"
        }
        numberBox54.setOnClickListener {
            speakText("54")  // Speak the letter "A"
        }
        numberBox55.setOnClickListener {
            speakText("55")  // Speak the letter "A"
        }
        numberBox56.setOnClickListener {
            speakText("56")  // Speak the letter "A"
        }
        numberBox57.setOnClickListener {
            speakText("57")  // Speak the letter "A"
        }
        numberBox58.setOnClickListener {
            speakText("58")  // Speak the letter "A"
        }
        numberBox59.setOnClickListener {
            speakText("59")  // Speak the letter "A"
        }
        numberBox60.setOnClickListener {
            speakText("60")  // Speak the letter "A"
        }
        numberBox61.setOnClickListener {
            speakText("61")  // Speak the letter "A"
        }
        numberBox62.setOnClickListener {
            speakText("62")  // Speak the letter "A"
        }
        numberBox63.setOnClickListener {
            speakText("63")  // Speak the letter "A"
        }
        numberBox64.setOnClickListener {
            speakText("64")  // Speak the letter "A"
        }
        numberBox65.setOnClickListener {
            speakText("65")  // Speak the letter "A"
        }
        numberBox66.setOnClickListener {
            speakText("66")  // Speak the letter "A"
        }
        numberBox67.setOnClickListener {
            speakText("67")  // Speak the letter "A"
        }
        numberBox68.setOnClickListener {
            speakText("68")  // Speak the letter "A"
        }
        numberBox69.setOnClickListener {
            speakText("69")  // Speak the letter "A"
        }
        numberBox70.setOnClickListener {
            speakText("70")  // Speak the letter "A"
        }
        numberBox71.setOnClickListener {
            speakText("71")  // Speak the letter "A"
        }
        numberBox72.setOnClickListener {
            speakText("72")  // Speak the letter "A"
        }
        numberBox73.setOnClickListener {
            speakText("73")  // Speak the letter "A"
        }
        numberBox74.setOnClickListener {
            speakText("74")  // Speak the letter "A"
        }
        numberBox75.setOnClickListener {
            speakText("75")  // Speak the letter "A"
        }
        numberBox76.setOnClickListener {
            speakText("76")  // Speak the letter "A"
        }
        numberBox77.setOnClickListener {
            speakText("77")  // Speak the letter "A"
        }
        numberBox78.setOnClickListener {
            speakText("78")  // Speak the letter "A"
        }
        numberBox79.setOnClickListener {
            speakText("79")  // Speak the letter "A"
        }
        numberBox80.setOnClickListener {
            speakText("80")  // Speak the letter "A"
        }
        numberBox81.setOnClickListener {
            speakText("81")  // Speak the letter "A"
        }
        numberBox82.setOnClickListener {
            speakText("82")  // Speak the letter "A"
        }
        numberBox83.setOnClickListener {
            speakText("83")  // Speak the letter "A"
        }
        numberBox84.setOnClickListener {
            speakText("84")  // Speak the letter "A"
        }
        numberBox85.setOnClickListener {
            speakText("85")  // Speak the letter "A"
        }
        numberBox86.setOnClickListener {
            speakText("86")  // Speak the letter "A"
        }
        numberBox87.setOnClickListener {
            speakText("87")  // Speak the letter "A"
        }
        numberBox88.setOnClickListener {
            speakText("88")  // Speak the letter "A"
        }
        numberBox89.setOnClickListener {
            speakText("89")  // Speak the letter "A"
        }
        numberBox90.setOnClickListener {
            speakText("90")  // Speak the letter "A"
        }
        numberBox91.setOnClickListener {
            speakText("91")  // Speak the letter "A"
        }
        numberBox92.setOnClickListener {
            speakText("92")  // Speak the letter "A"
        }
        numberBox93.setOnClickListener {
            speakText("93")  // Speak the letter "A"
        }
        numberBox94.setOnClickListener {
            speakText("94")  // Speak the letter "A"
        }
        numberBox95.setOnClickListener {
            speakText("95")  // Speak the letter "A"
        }
        numberBox96.setOnClickListener {
            speakText("96")  // Speak the letter "A"
        }
        numberBox97.setOnClickListener {
            speakText("97")  // Speak the letter "A"
        }
        numberBox98.setOnClickListener {
            speakText("98")  // Speak the letter "A"
        }
        numberBox99.setOnClickListener {
            speakText("99")  // Speak the letter "A"
        }
        numberBox100.setOnClickListener {
            speakText("100")  // Speak the letter "A"
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