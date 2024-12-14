package com.example.abakada.teacher.tabs.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.abakada.R
import com.example.abakada.databinding.FragmentQuizFormABinding


class QuizFormAFragment : Fragment() {
    private var _binding : FragmentQuizFormABinding? = null
    private val binding get() = _binding!!
    private var selectedQuizType: String? = null
    private var selectedQuizDifficulty: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizFormABinding.inflate(inflater, container, false)
        val optionsType = arrayOf("Fill in the blanks", "Matching")
        val optionsDifficulty = arrayOf("Easy", "Medium", "Hard")
        val adapterType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsType)
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.quizTypeSpinner.adapter = adapterType
        val adapterDifficulty = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsDifficulty)
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.quizDifficultySpinner.adapter = adapterDifficulty

        binding.quizTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedQuizType = parent.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.quizDifficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedQuizDifficulty = parent.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.nextButton.setOnClickListener {
            if(selectedQuizType != null && selectedQuizDifficulty != null && binding.quizNameInput.text.toString().isNotEmpty()) {
                val quizName = binding.quizNameInput.text.toString()
                val bundle = Bundle()
                bundle.putString("quizName", quizName)
                bundle.putString("quizType", selectedQuizType)
                bundle.putString("quizDifficulty", selectedQuizDifficulty)

                val addItemsFragment = AddItemsFragment()
                addItemsFragment.arguments = bundle

                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.quizzes_form_container, addItemsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }else{
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }


}