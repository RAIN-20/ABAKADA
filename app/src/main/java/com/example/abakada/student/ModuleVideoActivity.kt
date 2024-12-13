package com.example.abakada.student

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.R
import com.example.abakada.databinding.ActivityModuleVideoBinding

class ModuleVideoActivity : AppCompatActivity() {
    private var binding: ActivityModuleVideoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModuleVideoBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }
}