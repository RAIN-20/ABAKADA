package com.example.abakada.teacher.tabs.quizzes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.databinding.FragmentAddItemsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AddItemsFragment : Fragment() {
    private var _binding: FragmentAddItemsBinding? = null
    private val binding get() = _binding!!
    private lateinit var quizItemsAdapter: QuizItemsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemsBinding.inflate(inflater, container, false)
        val quizName = arguments?.getString("quizName") ?: ""
        val quizType = arguments?.getString("quizType") ?: ""
        binding.formType.text = quizType
        quizItemsAdapter = QuizItemsAdapter(binding.quizListItemContainer, quizType)
        binding.quizListItemContainer.adapter = quizItemsAdapter
        binding.quizListItemContainer.layoutManager =
            LinearLayoutManager(requireContext())

        binding.addQuizItemButton.setOnClickListener {
            if (quizType == "Fill in the Blacks"){
                quizItemsAdapter.addQuizItem(QuizQuestion.FillInTheBlanks("", ""))
            }else{
                quizItemsAdapter.addQuizItem(QuizQuestion.MatchingType("", ""))

            }

        }

        binding.submitButton.setOnClickListener {
            val quizData = quizItemsAdapter.getQuizData()

            val quizDifficulty = arguments?.getString("quizDifficulty") ?: ""

            val quiz = Quiz(
                name = quizName,
                type = quizType,
                difficulty = quizDifficulty,
                questions = quizData
            )
            Log.d("AddItemsFragment", "Quiz data: $quiz")
//            saveQuizToFirebase(quiz)
        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == QuizItemsAdapter.REQUEST_IMAGE_URL && resultCode == Activity.RESULT_OK) {
            val position = data?.getIntExtra("position", -1) ?: -1
            val imageUri = data?.data?.toString() ?: ""

            if (position != -1 && imageUri.isNotEmpty()) {
                quizItemsAdapter.updateImageUri(position, imageUri)
                quizItemsAdapter.notifyItemChanged(position)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveQuizToFirebase(quiz: Quiz) {
        val db = Firebase.firestore
        val quizzesCollection = db.collection("quizzes")

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