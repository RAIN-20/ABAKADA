package com.example.abakada.student

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.R
import com.example.abakada.databinding.ActivityModuleListDetailsBinding

class ModuleListDetailsActivity : AppCompatActivity() {
    private var binding: ActivityModuleListDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModuleListDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupEdgeToEdge()



    }
    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}