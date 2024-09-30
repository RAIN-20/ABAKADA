package com.example.abakada

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class StoryBooks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_books)


        val twoCats: Button = findViewById(R.id.twoCatsBtn)
        val theLion: Button = findViewById(R.id. theLionBtn)


        twoCats.setOnClickListener {
            val intent = Intent(this@StoryBooks, twoCatsStory::class.java)
            startActivity(intent)
        }

        theLion.setOnClickListener {
            val intent = Intent(this@StoryBooks, lionAndTheMouse::class.java)
            startActivity(intent)
        }


    }


}