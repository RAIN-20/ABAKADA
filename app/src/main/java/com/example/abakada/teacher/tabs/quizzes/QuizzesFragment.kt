package com.example.abakada.teacher.tabs.quizzes

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.databinding.FragmentQuizzesBinding
import com.google.firebase.firestore.FirebaseFirestore


class QuizzesFragment : Fragment() {
    private var _binding: FragmentQuizzesBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private lateinit var quizzesAdapter: QuizzesAdapter
    private var quizzesList = mutableListOf<Quiz>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        quizzesAdapter = QuizzesAdapter(quizzesList)
        binding.quizzesRecyclerView.adapter = quizzesAdapter
        binding.quizzesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchQuizzesData()

        binding.easyButton.setOnClickListener { filterQuizzes("Easy") }
        binding.mediumButton.setOnClickListener { filterQuizzes("Medium") }
        binding.hardButton.setOnClickListener { filterQuizzes("Hard") }

        binding.addQuizzesButton.setOnClickListener {
            val intent = Intent(requireContext(), CreateQuizActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    private fun fetchQuizzesData() {
        db.collection("quizzes")
            .get()
            .addOnSuccessListener { documents ->
                quizzesList.clear()
                for (document in documents) {
                    val quiz = document.toObject(Quiz::class.java)
                    quizzesList.add(quiz) // Add the quiz directly
                }
                quizzesAdapter.notifyDataSetChanged()

                // Show/hide empty view based on data
                binding.emptyView.visibility = if (quizzesList.isEmpty()) View.VISIBLE else View.GONE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    private fun filterQuizzes(difficulty: String) {
        val filteredQuizzes = quizzesList.filter { it.difficulty == difficulty }
        quizzesAdapter.updateQuizzes(filteredQuizzes)

        // Show/hide empty view based on filtered data
        binding.emptyView.visibility = if (filteredQuizzes.isEmpty()) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}