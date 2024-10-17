package com.example.abakada

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homeScreen)) { v, insets ->
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
//            val intent = Intent(this@HomeScreen, ModulesActivity::class.java)
//            startActivity(intent)
//        }
//
//        quizzesImage.setOnClickListener {
//            val intent = Intent(this@HomeScreen, QuizzesActivity::class.java)
//            startActivity(intent)
//        }

        storyBooksImage.setOnClickListener {
            val intent = Intent(this@HomeScreen, StoryBooks::class.java)
            startActivity(intent)
        }

        quizzesImage.setOnClickListener {
            val intent = Intent(this@HomeScreen, QuizzesActivity::class.java)
            startActivity(intent)
        }

        modulesImage.setOnClickListener {
            val intent = Intent(this@HomeScreen, modules_screen::class.java)
            startActivity(intent)
        }

        aboutUsImage.setOnClickListener {
            val intent = Intent(this@HomeScreen, AboutUs::class.java)
            startActivity(intent)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }



    }


}