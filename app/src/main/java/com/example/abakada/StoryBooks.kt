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
        val theUgly: Button = findViewById(R.id. theUglyBtn)
        val uglyytree: Button = findViewById(R.id. uglyTreeBtn)


        twoCats.setOnClickListener {
            val intent = Intent(this@StoryBooks, twoCatsStory::class.java)
            startActivity(intent)
        }

        theLion.setOnClickListener {
            val intent = Intent(this@StoryBooks, lionAndTheMouse::class.java)
            startActivity(intent)
        }

        theUgly.setOnClickListener {
            val intent = Intent(this@StoryBooks, uglyDuck::class.java)
            startActivity(intent)
        }

        uglyytree.setOnClickListener {
            val intent = Intent (this@StoryBooks, uglytree::class.java)
            startActivity(intent)
        }

    }


}