package com.example.abakada.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abakada.R
import com.example.abakada.databinding.FragmentMatchingTypeBinding

class MatchingTypeFragment : Fragment() {
    private var _binding: FragmentMatchingTypeBinding? = null
    private val binding get() = _binding!!
    val quizData = (activity as? QuizActivity)?.quizData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchingTypeBinding.inflate(inflater, container, false)

        return binding.root
    }

}