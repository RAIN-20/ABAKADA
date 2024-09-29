package com.example.abakada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class animals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals)

        val nextButton: Button = findViewById(R.id.nextButton)
//        val previousButton: Button = findViewById(R.id.previousButton)


        nextButton.setOnClickListener {
            val intent = Intent(this@animals, animals2::class.java)
            startActivity(intent)
        }


    }
}