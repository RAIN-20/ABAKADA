package com.example.abakada.teacher.tabs.stories

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.abakada.R
import com.example.abakada.databinding.ActivityAddStoriesBinding
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import kotlin.collections.map
class AddStoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoriesBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storyParts = mutableListOf<StoryPart>()
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                imageUri?.let { uri ->
                    // Find the corresponding StoryPart and store the Uri
                    val index = storyParts.indexOfFirst { it.imageUrl == null }
                    if (index != -1) {
                        storyParts[index].imageUrl = uri
                        // You can optionally display a placeholder or thumbnail here
                        storyParts[index].imageView?.setImageURI(uri)
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Set click listeners
        binding.addPartButton.setOnClickListener { addStoryPart() }
        binding.saveStoryButton.setOnClickListener { saveStory() }

        // Set window insets listener
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addStoryPart() {
        val storyPartLayout = layoutInflater.inflate(R.layout.story_part_layout, binding.storyPartsContainer, false)
        val selectImageButton = storyPartLayout.findViewById<ImageView>(R.id.select_image_button)
        val removePartButton = storyPartLayout.findViewById<ImageButton>(R.id.removePartButton)
        val partTextEditText = storyPartLayout.findViewById<EditText>(R.id.partTextEditText)
        selectImageButton.setOnClickListener {
            openGallery()
            val index = binding.storyPartsContainer.indexOfChild(storyPartLayout)
            storyParts[index].imageUrl = null
        }

        removePartButton.setOnClickListener {
            binding.storyPartsContainer.removeView(storyPartLayout)
            storyParts.remove(StoryPart(null, ""))
        }

        partTextEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // Update the text property of the corresponding StoryPart object
                val index = binding.storyPartsContainer.indexOfChild(storyPartLayout)
                storyParts[index].text = s?.toString() ?: ""
            }
        })

        binding.storyPartsContainer.addView(storyPartLayout)
        storyParts.add(StoryPart(null, "", null))
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageUri: Uri? = data?.data
        imageUri?.let { uri ->
            // Find the corresponding ImageView using the index
            val index = storyParts.indexOfFirst { it.imageView != null && it.imageUrl == null }
            if (index != -1) {
                storyParts[index].imageUrl = uri
                storyParts[index].imageView?.setImageURI(uri)
            }
        }
    }
    private fun openGallery() {
        val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(pickImageIntent)
    }

    private fun saveStory() {
        val storyTitle = binding.storyTitleEditText.text.toString()

        // Validate data (ensure title and parts are filled)
        if (storyTitle.isEmpty() || storyParts.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        uploadImagesAndSaveStory(storyTitle)
    }
    private fun uploadImagesAndSaveStory(storyTitle: String) {
        val uploadTasks = mutableListOf<Pair<Int, Task<Uri>>>()

        for ((index, storyPart) in storyParts.withIndex()) {
            storyPart.imageUrl?.let { imageUri ->
                val storageRef = storage.reference.child("story_images/${UUID.randomUUID()}")
                val uploadTask = storageRef.putFile(imageUri).continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageRef.downloadUrl
                }
                uploadTasks.add(Pair(index, uploadTask))
            }
        }

        Tasks.whenAllSuccess<Uri>(uploadTasks.map { it.second })
            .addOnSuccessListener { downloadUrls ->
                for ((i, downloadUrl) in downloadUrls.withIndex()) {
                    storyParts[uploadTasks[i].first].imageUrl = downloadUrl
                }

                // Create a map to store story data
                val storyData = hashMapOf(
                    "title" to storyTitle,
                    "parts" to storyParts.map { hashMapOf("imageUrl" to it.imageUrl, "text" to it.text) },
                    "created_at" to FieldValue.serverTimestamp()
                )

                // Save story data to Firestore
                firestore.collection("stories").add(storyData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Story saved successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to save story", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                // Handle upload failure
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
    }
}