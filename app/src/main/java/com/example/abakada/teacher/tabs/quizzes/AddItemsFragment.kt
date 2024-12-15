package com.example.abakada.teacher.tabs.quizzes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.databinding.FragmentAddItemsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddItemsFragment : Fragment() {
    private var _binding: FragmentAddItemsBinding? = null
    private val binding get() = _binding!!
    private lateinit var quizItemsAdapter: QuizItemsAdapter
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemsBinding.inflate(inflater, container, false)

        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (quizItemsAdapter.currentAdapterPosition != -1 && data?.data != null) {
                    val imageUriString = data.data.toString()
                    quizItemsAdapter.updateImageUri(quizItemsAdapter.currentAdapterPosition, imageUriString)
                }
            }
        }

        val quizName = arguments?.getString("quizName") ?: ""
        val quizType = arguments?.getString("quizType") ?: ""
        binding.formType.text = quizType
        quizItemsAdapter = QuizItemsAdapter(binding.quizListItemContainer, quizType, startForResult)
        binding.quizListItemContainer.adapter = quizItemsAdapter
        binding.quizListItemContainer.layoutManager = LinearLayoutManager(requireContext())
        binding.addQuizItemButton.setOnClickListener {
            val quizQuestion = QuizQuestion(
                questionType = if (quizType == "Matching") "Matching" else "Fill in the blanks",
                question = "",
                answer = "",
                imgUri = null
            )
            quizItemsAdapter.addQuizItem(quizQuestion)
        }


        binding.submitButton.setOnClickListener {
            val quizData = quizItemsAdapter.getQuizData()

            val quizDifficulty = arguments?.getString("quizDifficulty") ?: ""

            val quiz = Quiz(
                id = UUID.randomUUID().toString(),
                name = quizName,
                type = quizType,
                difficulty = quizDifficulty,
                questions = quizData
            )
            saveQuizToFirebase(quiz)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveQuizToFirebase(quiz: Quiz) {
        val db = Firebase.firestore
        val quizzesCollection = db.collection("quizzes")
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        val questionsWithImages = quiz.questions.filter { it.imgUri != null }

        if (questionsWithImages.isEmpty()) {
            saveQuizToFirestore(quizzesCollection, quiz)
            return
        }

        var uploadCount = 0

        questionsWithImages.forEach { question ->
            val imageRef = storageRef.child("quiz_images/${question.imgUri}")
            val uploadTask = imageRef.putFile(Uri.parse(question.imgUri))

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Update imgUri with download URL
                    question.imgUri = downloadUri.toString()
                    uploadCount++

                    if (uploadCount == questionsWithImages.size) {
                        // All images uploaded, save quiz to Firestore
                        saveQuizToFirestore(quizzesCollection, quiz)
                    }
                }.addOnFailureListener { exception ->
                    // Handle download URL retrieval failure
                    Log.e("AddItemsFragment", "Error getting download URL", exception)
                    Toast.makeText(requireContext(), "Error saving quiz", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { exception ->
                // Handle image upload failure
                Log.e("AddItemsFragment", "Error uploading image", exception)
                Toast.makeText(requireContext(), "Error saving quiz", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveQuizToFirestore(quizzesCollection: CollectionReference, quiz: Quiz) {
        quizzesCollection.add(quiz)
            .addOnSuccessListener { documentReference ->
                Log.d("AddItemsFragment", "Quiz saved with ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Quiz saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("AddItemsFragment", "Error saving quiz", e)
                Toast.makeText(requireContext(), "Error saving quiz", Toast.LENGTH_SHORT).show()
            }
    }
}