package com.example.abakada.teacher.tabs.modules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.abakada.R
import com.example.abakada.databinding.FragmentModuleFormListBinding
import com.example.abakada.teacher.tabs.stories.StoryPart
import com.example.abakada.utils.LoadingOverlayUtils
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ModuleFormListFragment : Fragment() {
    private var _binding: FragmentModuleFormListBinding? = null
    private val binding get() = _binding!!
    private val moduleParts = mutableListOf<ModulePart>()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private var currentModuleListItem: ModulePart? = null
    private val viewModel: AddModulesViewModel by activityViewModels()
    private val pickImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            currentModuleListItem?.imageUri = selectedImageUri
            val selectImageButton = currentModuleListItem?.let { moduleListItem ->
                val listVisualForm = binding.moduleListItemContainer.getChildAt(moduleParts.indexOf(moduleListItem))
                listVisualForm?.findViewById<ImageView>(R.id.select_image_button)
            }
            selectImageButton?.setImageURI(selectedImageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModuleFormListBinding.inflate(inflater, container, false)

        binding.addModuleItemButton.setOnClickListener {
            val modulePart = ModulePart("", "", null)
            val modulePartView = inflater.inflate(R.layout.layout_list_visual, binding.moduleListItemContainer, false)
            val removeButton = modulePartView.findViewById<ImageView>(R.id.removePartButton)
            val partNameEditText = modulePartView.findViewById<EditText>(R.id.partTextEditText)
            val partNameEditTextDescription = modulePartView.findViewById<EditText>(R.id.partTextEditTextDescription)
            val selectImageButton = modulePartView.findViewById<ImageView>(R.id.select_image_button)
            if (modulePartView.parent != null) {
                (modulePartView.parent as ViewGroup).removeView(modulePartView)
            }
            removeButton.setOnClickListener {
                binding.moduleListItemContainer.removeView(modulePartView)
                moduleParts.remove(ModulePart("", "", null))
            }

            partNameEditText.doAfterTextChanged { editable ->
                modulePart.name = editable?.toString() ?: ""
            }
             partNameEditTextDescription.doAfterTextChanged { editable ->
                modulePart.description = editable?.toString() ?: ""
            }

            selectImageButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "image/*"
                    addCategory(Intent.CATEGORY_OPENABLE)
                }
                currentModuleListItem = modulePart
                pickImage.launch(intent)
            }

            binding.moduleListItemContainer.addView(modulePartView)
            moduleParts.add(modulePart)
        }


        binding.submitButton.setOnClickListener {
            val data = viewModel.moduleData.value
            if (data != null) {
                data.parts = moduleParts
                viewModel.moduleData.value = data
                Log.d("ModuleFormListFragment", "Data: $data")

                uploadAndSaveModuleData(data)
            }
        }
        return binding.root
    }
    private fun uploadImagesAndGetLinks(moduleParts: List<ModulePart>): Task<List<String?>> {
        val uploadTasks = moduleParts.mapIndexed { index, moduleListItem ->
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
                        task.result.toString()
                    }
            } else {
                Tasks.forResult(null)
            }
        }

        return Tasks.whenAllSuccess<String?>(uploadTasks)
    }

    private fun uploadAndSaveModuleData(data: ModuleData) {
        LoadingOverlayUtils.showLoadingOverlay(requireActivity())
        val moduleImageUploadTask = uploadModuleImage(data.imageUrl)
        val partImagesUploadTask = uploadImagesAndGetLinks(data.parts)

        Tasks.whenAllSuccess<Any>(moduleImageUploadTask, partImagesUploadTask) // Use Any for mixed types
            .addOnSuccessListener { results ->
                val moduleImageUrl = results[0] as? String // Access results by index
                val partImageUrls = results[1] as? List<String?> // Access results by index

                val updatedParts = data.parts.mapIndexed { index, modulePart ->
                    modulePart.copy(imageUri = partImageUrls?.getOrNull(index)?.let { Uri.parse(it) })
                }
                val updatedData = data.copy(parts = updatedParts.toMutableList(), imageUrl = moduleImageUrl?.let { Uri.parse(it) })

                saveModuleDataToFirestore(updatedData)
            }
            .addOnFailureListener { e ->
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
                Toast.makeText(requireContext(), "Error uploading images: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun uploadModuleImage(imageUrl: Uri?): Task<String?> {
        return if (imageUrl != null) {
            val moduleImageRef = storage.reference.child("module_images/${System.currentTimeMillis()}_module_image.jpg")
            moduleImageRef.putFile(imageUrl)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    moduleImageRef.downloadUrl
                }
                .continueWith { task -> task.result.toString() }
        } else {
            LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
            Tasks.forResult(null)

        }
    }

    private fun saveModuleDataToFirestore(moduleData: ModuleData) {
        val modulesCollection = db.collection("modules")
        modulesCollection.add(moduleData)
            .addOnSuccessListener { documentReference ->
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
                Toast.makeText(requireContext(), "Module data saved", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error saving module data: ${e.message}", Toast.LENGTH_SHORT).show()
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}