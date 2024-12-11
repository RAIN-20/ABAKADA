package com.example.abakada.teacher.tabs.modules

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.abakada.databinding.FragmentModuleFormVideoBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class ModuleFormVideoFragment : Fragment() {
    private var _binding: FragmentModuleFormVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModuleFormVideoBinding.inflate(inflater, container, false)
        val viewModel: AddModulesViewModel by activityViewModels()

        val moduleVideo = ModuleVideo(
            binding.linkInput.text.toString(),
            binding.videoDescriptionInput.text.toString(),)

        viewModel.moduleData.value?.video = moduleVideo
        binding.submitButton.setOnClickListener {
            val data = viewModel.moduleData.value
            if (data != null) {
                saveModuleDataToFirestore(data)
            }
        }

        return binding.root
    }
    private fun saveModuleDataToFirestore(moduleData: ModuleData) {
        val db = Firebase.firestore
        val modulesCollection = db.collection("modules")

        modulesCollection.add(moduleData)
            .addOnSuccessListener { documentReference ->
                Log.d("ModuleFormVideoFragment", "Module data saved with ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Module data saved", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
            .addOnFailureListener { e ->
                Log.w("ModuleFormVideoFragment", "Error saving module data", e)
                Toast.makeText(requireContext(), "Error saving module data. Please Try Again later", Toast.LENGTH_SHORT).show()
//                parentFragmentManager.popBackStack()
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}