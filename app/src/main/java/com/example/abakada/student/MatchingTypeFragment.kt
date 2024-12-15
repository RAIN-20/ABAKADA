package com.example.abakada.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.abakada.databinding.FragmentMatchingTypeBinding

class MatchingTypeFragment : Fragment() {
    private var _binding: FragmentMatchingTypeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedQuizViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchingTypeBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quizData = sharedViewModel.quizData
        val questions = quizData?.questions ?: emptyList()

        for (question in questions) {
            // Create ImageView for the image
            val imageView = ImageView(requireContext())
            Glide.with(this)
                .load(question.imgUri)
                .into(imageView)
            binding.imagesContainer.addView(imageView)

            // Create TextView for the answer option
            val textView = TextView(requireContext())
            textView.text = question.answer
            binding.answersContainer.addView(textView)
        }

    }
}