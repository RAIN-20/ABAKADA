package com.example.abakada.teacher.tabs.modules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.abakada.R
import com.example.abakada.databinding.FragmentModuleFormListBinding
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ModuleFormListFragment : Fragment() {
    private var _binding: FragmentModuleFormListBinding? = null
    private val binding get() = _binding!!
    private val moduleListItems = mutableListOf<ModuleList>()
    private var currentModuleListItem: ModuleList? = null
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val viewModel: AddModulesViewModel by activityViewModels()
    private val pickImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            currentModuleListItem?.imageUri = selectedImageUri // Update the imageUri
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModuleFormListBinding.inflate(inflater, container, false)

        binding.addModuleItemButton.setOnClickListener {
            val listVisualForm = inflater.inflate(R.layout.layout_list_visual, binding.moduleListItemContainer, false)
            binding.moduleListItemContainer.addView(listVisualForm)

            val removeButton = listVisualForm.findViewById<ImageView>(R.id.removePartButton)
            removeButton.setOnClickListener {
                binding.moduleListItemContainer.removeView(listVisualForm)
            }

            val partTextEditText = listVisualForm.findViewById<EditText>(R.id.partTextEditText)
            val selectImageButton = listVisualForm.findViewById<ImageView>(R.id.select_image_button)
            selectImageButton.setOnClickListener {
                currentModuleListItem = ModuleList(partTextEditText.text.toString(), null) // Create and store reference
                moduleListItems.add(currentModuleListItem!!) // Add to the list
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickImage.launch(intent)
            }
        }


        binding.submitButton.setOnClickListener {
            val data = viewModel.moduleData.value
            if(data != null) {
                uploadImagesAndGetLinks(data.parts)
                    .addOnSuccessListener { imageLinks ->
                        // Update ModuleData with image links
                        val updatedData = data.copy(
                            parts = moduleListItems.mapIndexed { index, moduleList ->
                                moduleList.copy(imageUri = imageLinks.getOrNull(index)?.let { Uri.parse(it) })
                            }
                        )
                        Log.d("ModuleFormListFragment", "Updated Data: $updatedData")

                        // Upload updated data to Firestore
                        val modulesCollection = db.collection("modules")
                        modulesCollection.add(updatedData)
                            .addOnSuccessListener { documentReference ->
                               Toast.makeText(requireContext(), "Module data saved", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                               Toast.makeText(requireContext(), "Error saving module data. Please Try Again later", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Error uploading images. Please Try Again later", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        return binding.root
    }
    private fun uploadImagesAndGetLinks(moduleListItems: List<ModuleList>): Task<List<String?>> {
        val uploadTasks = moduleListItems.mapIndexed { index, moduleListItem ->
            val imageUri = moduleListItem.imageUri
            if (imageUri != null) {
                val storageRef: StorageReference = storage.reference
                    .child("module_images")
                    .child("${System.currentTimeMillis()}/part_${index + 1}.jpg") // Create unique file name

                storageRef.putFile(imageUri)
                    .continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        storageRef.downloadUrl
                    }
                    .continueWith { task ->
                        task.result.toString() // Get download URL as String
                    }
            } else {
                Tasks.forResult(null) // Return null if no image
            }
        }

        return Tasks.whenAllSuccess<String?>(uploadTasks) // Combine all tasks
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}