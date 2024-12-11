package com.example.abakada.teacher.tabs.modules

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.R
import com.example.abakada.databinding.ActivityAddModulesBinding

class AddModulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddModulesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddModulesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}