package com.example.abakada.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.abakada.R
import com.example.abakada.databinding.FragmentFillInTheBlanksBinding
import com.example.abakada.teacher.tabs.quizzes.Quiz
import com.google.gson.Gson


class FillInTheBlanksFragment : Fragment() {
    private var _binding: FragmentFillInTheBlanksBinding? = null
    private val binding get() = _binding!!
//    val quizData = (activity as? QuizActivity)?.quizData
    private val sharedViewModel: SharedQuizViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFillInTheBlanksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val quizDataJson = arguments?.getString("quiz_data_json")
//        val quizData = Gson().fromJson(quizDataJson, Quiz::class.java)
        val quizData = sharedViewModel.quizData
        val questions = quizData?.questions ?: emptyList()
        val questionViewPager = binding.questionViewPager
        val adapter = QuestionPagerAdapter(questions)
        questionViewPager.adapter = adapter
    }

}