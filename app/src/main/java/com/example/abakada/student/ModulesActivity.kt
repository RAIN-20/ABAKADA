package com.example.abakada.student

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.databinding.ActivityModulesBinding
import com.example.abakada.teacher.tabs.modules.ModuleData
import com.example.abakada.teacher.tabs.modules.ModuleListAdapter
import com.example.abakada.teacher.tabs.modules.ModulePart
import com.example.abakada.teacher.tabs.modules.ModuleVideo
import com.google.firebase.firestore.FirebaseFirestore

class ModulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModulesBinding
    private val moduleList = mutableListOf<ModuleData>()
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ModuleListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityModulesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()
        adapter = ModuleListAdapter()
        binding.modulesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.modulesRecyclerView.adapter = adapter
        fetchModulesFromFirebase()
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun fetchModulesFromFirebase() {
        db.collection("modules")
            .get()
            .addOnSuccessListener { documents ->
                moduleList.clear()
                for (document in documents) {
                    val moduleName = document.getString("name") ?: ""
                    val moduleId = document.id
                    val moduleImageUrl = document.getString("imageUrl")?.toUri()
                    val moduleType = document.getString("type") ?: "Unknown Type"
                    val moduleParts = document.get("parts") as? MutableList<ModulePart> ?: mutableListOf()
                    val moduleVideo = document.get("video") as? ModuleVideo
                    val moduleDescription = document.getString("description") ?: ""

                    val moduleData = ModuleData(
                        id = moduleId,
                        name = moduleName,
                        imageUrl = moduleImageUrl,
                        type = moduleType,
                        description = moduleDescription,
                        parts = moduleParts,
                        video = moduleVideo?: ModuleVideo("", "")
                    )
                    Log.d(TAG, "Story Title: $moduleName")
                    moduleList.add(moduleData)
                }
                adapter.submitList(moduleList)
                if (moduleList.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.modulesRecyclerView.visibility = View.GONE
                    Log.d(TAG, "No Modules found")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "Error fetching stories", Toast.LENGTH_SHORT).show()
            }
    }
}