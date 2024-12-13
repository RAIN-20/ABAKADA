package com.example.abakada.teacher.tabs.modules

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.databinding.FragmentModulesBinding
import com.google.firebase.firestore.FirebaseFirestore

class ModulesFragment : Fragment() {
    private var _binding: FragmentModulesBinding? = null
    private val binding get() = _binding!!
    private val moduleList = mutableListOf<ModuleData>()
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: ModuleListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModulesBinding.inflate(inflater, container, false)

        db = FirebaseFirestore.getInstance()
        adapter = ModuleListAdapter()
        binding.modulesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.modulesRecyclerView.adapter = adapter
        fetchModulesFromFirebase()

        binding.addModulesButton.setOnClickListener {
            val intent = Intent(requireContext(), AddModulesActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }
    private fun fetchModulesFromFirebase() {
        db.collection("modules")
            .get()
            .addOnSuccessListener { documents ->
                moduleList.clear()
                for (document in documents) {
                    val moduleName = document.getString("name")
                    val moduleId = document.id
                    val moduleImageUrl = document.getString("imageUrl")?.toUri()
                    val moduleData = ModuleData(id = moduleId, name = moduleName!!, imageUrl = moduleImageUrl)
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
                Toast.makeText(requireContext(), "Error fetching stories", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}