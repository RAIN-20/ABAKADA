package com.example.abakada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class animals2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals2)

        val previousButton: Button = findViewById(R.id.previousButton)



        previousButton.setOnClickListener {
            val intent = Intent (this@animals2, animals::class.java)
            startActivity(intent)
        }


    }
}