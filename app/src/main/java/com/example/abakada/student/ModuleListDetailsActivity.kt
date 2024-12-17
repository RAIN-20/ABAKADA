package com.example.abakada.student

import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.databinding.ActivityModuleListDetailsBinding
import com.example.abakada.teacher.tabs.modules.ModulePart
import com.google.firebase.firestore.FirebaseFirestore

class ModuleListDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModuleListDetailsBinding
    private val partsList = mutableListOf<ModulePart>()
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModuleListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moduleName = intent.getStringExtra("moduleName")
        val moduleImg = intent.getStringExtra("moduleImg")
        val moduleId = intent.getStringExtra("moduleId")

        Glide.with(this)
            .load(moduleImg)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(binding.moduleImage)

        fetchModuleDetails(moduleId!!)

        binding.moduleName.text = moduleName

        binding.backButton.setOnClickListener {
            finish()
        }

    }
    private fun fetchModuleDetails(moduleId: String) {
        db = FirebaseFirestore.getInstance()

        db.collection("modules").document(moduleId).get().addOnSuccessListener { document ->
            if (document != null) {
                val parts = document.get("parts") as? List<Map<String, Any>>
                if (parts != null) {
                    partsList.clear()
                    for (partMap in parts) {
                        val name = partMap["name"] as? String ?: ""
                        val imageUri = partMap["imageUri"] as? String ?: ""
                        val description = partMap["description"] as? String ?: ""
                        val modulePart = ModulePart(name = name, description = description, imageUri = imageUri.toUri())
                        partsList.add(modulePart)
                    }

                    val partsContainer = findViewById<GridLayout>(R.id.parts_container)
                    val adapter = PartsAdapter(this, partsList)

                    for (i in 0 until partsList.size) {
                        val partView = adapter.getView(i, null, partsContainer)
                        partsContainer.addView(partView)
                    }
                }
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error fetching module details", Toast.LENGTH_SHORT).show()
        }

    }
}