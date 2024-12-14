package com.example.abakada.teacher.tabs.modules

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import com.example.abakada.databinding.FragmentModuleFormVideoBinding
import com.example.abakada.utils.LoadingOverlayUtils
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID


class ModuleFormVideoFragment : Fragment() {
    private var _binding: FragmentModuleFormVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModuleFormVideoBinding.inflate(inflater, container, false)
        val viewModel: AddModulesViewModel by activityViewModels()


        binding.submitButton.setOnClickListener {
            val moduleVideo = ModuleVideo(
                binding.linkInput.text.toString(),
                binding.videoDescriptionInput.text.toString(),
            )

            viewModel.moduleData.value?.video = moduleVideo

            val data = viewModel.moduleData.value
            if (data != null) {
                uploadImageToStorageAndSaveToFirestore(viewModel.moduleData.value!!.name, viewModel.moduleData.value!!.imageUrl!!
                    , viewModel.moduleData.value!!.video.description, viewModel.moduleData.value!!.video.link)
            }
        }

        return binding.root
    }
    private fun saveModuleDataToFirestore(
        moduleName: String,
        imageUrl: String,
        videoDescription: String,
        videoLink: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val modulesCollection = db.collection("modules")

        val moduleData = hashMapOf(
            "name" to moduleName,
            "imageUrl" to imageUrl,
            "type" to "Video",
            "description" to videoDescription,
            "video" to ModuleVideo(link = videoLink, description = videoDescription)
        )

        modulesCollection.add(moduleData)
            .addOnSuccessListener { documentReference ->
                Log.d("ModuleVideoActivity", "Module data saved with ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Module data saved", Toast.LENGTH_SHORT).show()
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
                requireActivity().finish()
            }
            .addOnFailureListener { e ->
                Log.w("ModuleVideoActivity", "Error saving module data", e)
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
                Toast.makeText(requireContext(), "Error saving module data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun uploadImageToStorageAndSaveToFirestore(
        moduleName: String,
        moduleImgUri: Uri,
        videoDescription: String,
        videoLink: String
    ) {
        LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("module_images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(moduleImgUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result
                saveModuleDataToFirestore(moduleName, downloadUrl.toString(), videoDescription, videoLink)
            } else {
                // Handle failures
                LoadingOverlayUtils.hideLoadingOverlay(requireActivity())
                Log.w("ModuleVideoActivity", "Image upload failed: ", task.exception)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}