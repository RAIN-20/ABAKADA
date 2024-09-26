package com.example.abakada

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val storyBooksImage: ImageView = findViewById(R.id.image_story_books)
        val modulesImage: ImageView = findViewById(R.id.image_modules)
        val quizzesImage: ImageView = findViewById(R.id.image_quizzes)
        val aboutUsImage: ImageView = findViewById(R.id.image_about_us)

//        storyBooksImage.setOnClickListener {
//            val intent = Intent(this@HomeScreen, StoryBooksActivity::class.java)
//            startActivity(intent)
//        }
//
//        modulesImage.setOnClickListener {
//            val intent = Intent(this@HomeScreenActivity, ModulesActivity::class.java)
//            startActivity(intent)
//        }
//
//        quizzesImage.setOnClickListener {
//            val intent = Intent(this@HomeScreenActivity, QuizzesActivity::class.java)
//            startActivity(intent)
//        }

        aboutUsImage.setOnClickListener {
            val intent = Intent(this@HomeScreen, AboutUs::class.java)
            startActivity(intent)
        }

    }
}