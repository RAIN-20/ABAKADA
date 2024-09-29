package com.example.abakada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class modules_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modules_screen)

        val numbersImage: ImageView = findViewById(R.id.numberIcon)
        val numbersText: Button = findViewById(R.id.numbersButton)

        val lettersImage: ImageView = findViewById(R.id.letterIcon)
        val lettersText: Button = findViewById(R.id.lettersButton)

        val animalsImage: ImageView = findViewById(R.id. animalsIcon)
        val animalsText: Button = findViewById(R.id. animalsButton)

        val shapesImage: ImageView = findViewById(R.id. shapesIcon)
        val shapesButton: Button = findViewById(R.id. shapesButton)


        numbersImage.setOnClickListener {
            val intent = Intent(this@modules_screen, numbers::class.java)
            startActivity(intent)
        }

        numbersText.setOnClickListener {
            val intent = Intent(this@modules_screen, numbers::class.java)
            startActivity(intent)
        }
        lettersImage.setOnClickListener {
            val intent = Intent(this@modules_screen, letters::class.java)
            startActivity(intent)
        }

        lettersText.setOnClickListener {
            val intent = Intent(this@modules_screen, letters::class.java)
            startActivity(intent)
        }

        animalsImage.setOnClickListener {
            val intent = Intent(this@modules_screen, animals::class.java)
            startActivity(intent)
        }

        animalsText.setOnClickListener {
            val intent = Intent(this@modules_screen, animals::class.java)
            startActivity(intent)
        }


        shapesImage.setOnClickListener {
            val intent= Intent(this@modules_screen, shapes::class.java)
            startActivity(intent)
        }

        shapesButton.setOnClickListener {
            val intent= Intent(this@modules_screen, shapes::class.java)
            startActivity(intent)
        }



    }


}