package com.example.abakada.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.abakada.databinding.FragmentFillInTheBlanksBinding
import com.example.abakada.teacher.tabs.quizzes.QuizQuestion
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FillInTheBlanksFragment : Fragment() {
    private var _binding: FragmentFillInTheBlanksBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedQuizViewModel by activityViewModels()
    private var finishedItems = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFillInTheBlanksBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener {
            val score = calculateScore()
            val quizId = sharedViewModel.quizData?.id ?: ""

            saveQuizResult(score, quizId)
//            TODO REDIRECT TO SHOW QUIZ RESULTS
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quizData = sharedViewModel.quizData
        val questions = quizData?.questions ?: emptyList()
        val questionViewPager = binding.questionViewPager
        val adapter = QuestionPagerAdapter(questions, questionViewPager)
        questionViewPager.adapter = adapter

        questionViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                finishedItems = position + 1
                updateItemCountText(questions)
            }
        })

        updateItemCountText(questions)
    }
    private fun updateItemCountText(questions: List<QuizQuestion>) {
        val totalItems = questions.size
        binding.quizItemCount.text = "$finishedItems / $totalItems"
        if (finishedItems == totalItems) {
            binding.submitButton.visibility = View.VISIBLE
        } else {
            binding.submitButton.visibility = View.GONE
        }
    }

    private fun saveQuizResult(score: Int, quizId: String) {
        val db = FirebaseFirestore.getInstance()
        val userId = getCurrentUserId() // Replace with your logic to get the user ID
        Log.d("FillInTheBlanksFragment", "Score: $score")
        val quizResult = hashMapOf(
            "userId" to userId,
            "quizId" to quizId,
            "score" to score,
            "timestamp" to Timestamp.now()
        )

        db.collection("quizResults")
            .add(quizResult)
            .addOnSuccessListener { documentReference ->
                Log.d("FillInTheBlanksFragment", "Quiz result saved with ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Quiz result saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("FillInTheBlanksFragment", "Error saving quiz result", e)
                Toast.makeText(requireContext(), "Error saving quiz result", Toast.LENGTH_SHORT).show()
            }
    }
    private fun calculateScore(): Int {
        var score = 0
        val questions = sharedViewModel.quizData?.questions ?: emptyList()
        val adapter = binding.questionViewPager.adapter as? QuestionPagerAdapter

        for (i in questions.indices) {
            val question = questions[i]
            val userAnswer = adapter?.getUserAnswer(i) ?: ""
            Log.d("FillInTheBlanksFragment", "User answer for question ${question.answer}: $userAnswer")
            if (userAnswer.equals(question.answer, ignoreCase = true)) {
                score++
            }
        }

        return score
    }

    private fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }
}