package com.example.abakada.teacher.tabs.modules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.abakada.R
import com.example.abakada.databinding.FragmentModuleFormABinding

class ModuleFormAFragment : Fragment() {
    private var _binding: FragmentModuleFormABinding? = null
    private val binding get() = _binding!!
    private var selectedModuleType: String? = null
    private val viewModel: AddModulesViewModel by activityViewModels()
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            viewModel.moduleData.value?.imageUrl = selectedImageUri
            binding.selectImageButton.setImageURI(selectedImageUri)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModuleFormABinding.inflate(inflater, container, false)

        val options = arrayOf("List/Visual", "Video")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.moduleTypeSpinner.adapter = adapter

        binding.moduleTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedModuleType = parent.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        }


        binding.nextButton.setOnClickListener {
            viewModel.moduleData.value?.name = binding.moduleNameInput.text.toString()
            viewModel.moduleData.value?.description = binding.moduleDescriptionInput.text.toString()
            viewModel.moduleData.value?.type = selectedModuleType!!
            when (selectedModuleType) {
                "List/Visual" -> {
                    val listVisualFragment = ModuleFormListFragment() // Replace with your fragment class
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.module_types_container, listVisualFragment) // Replace with your container ID
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                "Video" -> {
                    val videoFragment = ModuleFormVideoFragment() // Replace with your fragment class
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.module_types_container, videoFragment) // Replace with your container ID
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                else -> {

                }
            }
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}