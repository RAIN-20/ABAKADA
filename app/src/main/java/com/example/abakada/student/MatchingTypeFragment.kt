package com.example.abakada.student

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.ResultsActivity
import com.example.abakada.databinding.FragmentMatchingTypeBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import de.hdodenhof.circleimageview.CircleImageView

class MatchingTypeFragment : Fragment() {
    private var _binding: FragmentMatchingTypeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedQuizViewModel by activityViewModels()
    private var correctAnswers = 0
    private var totalItems = 0
    private lateinit var db: FirebaseFirestore
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
        totalItems = questions.size
        binding.quizItemCount.text = "0 / $totalItems"
        val randomizedQuestions = questions.shuffled()
        for (i in randomizedQuestions.indices) {
            val question = randomizedQuestions[i]
            // Create ImageView for the image
            val imageView = CircleImageView(requireContext())
            Glide.with(this)
                .load(question.imgUri)
                .into(imageView)
            imageView.tag = i

            val imageLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageLayoutParams.gravity = Gravity.CENTER_HORIZONTAL
            imageView.layoutParams = imageLayoutParams

            imageView.setOnLongClickListener { v ->
                val clipText = v.tag.toString()
                val itemData = ClipData.Item(clipText)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val data = ClipData(clipText, mimeTypes, itemData)

                val dragShadowBuilder = View.DragShadowBuilder(v)
                v.startDragAndDrop(data, dragShadowBuilder, v, 0)

                true
            }

            binding.imagesContainer.addView(imageView)

            val textView = TextView(requireContext())
            textView.text = question.answer
            textView.tag = i
            textView.setTextAppearance(requireContext(), R.style.TextQuizzes)
            val textLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textLayoutParams.gravity = Gravity.CENTER_HORIZONTAL
            textView.layoutParams = textLayoutParams

            textView.setOnDragListener { v, event ->
                when (event.action) {
                    DragEvent.ACTION_DROP -> {
                        val clipData = event.clipData
                        val clipText = clipData.getItemAt(0).text.toString()
                        val draggedImageIndex = clipText.toInt()

                        if (draggedImageIndex == v.tag as Int) {
                            correctAnswers++
                            binding.quizItemCount.text = "$correctAnswers / $totalItems"
                            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
                            (v as TextView).setTextColor(Color.GREEN)
                        } else {
                            Toast.makeText(requireContext(), "Incorrect!", Toast.LENGTH_SHORT).show()
                        }

                        true
                    }
                    else -> true
                }
            }

            binding.answersContainer.addView(textView)

        }

        binding.submitButton.setOnClickListener {
            val userId = getCurrentUserId() // Replace with your logic to get userId
            val quizId = sharedViewModel.quizData?.id ?: "" // Get quizId from sharedViewModel
            val score = correctAnswers

            val quizResult = hashMapOf(
                "userId" to userId,
                "quizId" to quizId,
                "score" to score,
                "timestamp" to Timestamp.now()
            )
            db = Firebase.firestore
            db.collection("quizResults")
                .add(quizResult)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(requireContext(), "Quiz results submitted successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), ResultsActivity::class.java)
                    intent.putExtra("correctAnswers", correctAnswers)
                    intent.putExtra("totalQuestions", totalItems)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error submitting quiz results: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}