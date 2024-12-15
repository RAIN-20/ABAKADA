package com.example.abakada.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.teacher.tabs.quizzes.QuizQuestion

class QuestionPagerAdapter(private val questions: List<QuizQuestion>, private val viewPager: ViewPager2) :
    RecyclerView.Adapter<QuestionPagerAdapter.QuestionViewHolder>() {
    private val userAnswers = mutableMapOf<Int, String>()
    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val questionImageView: ImageView = itemView.findViewById(R.id.questionImageView)
        val answerEditText: TextView = itemView.findViewById(R.id.answerEditText)
        val nextButton: Button = itemView.findViewById(R.id.nextButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false) // Create your item layout
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]

        holder.questionTextView.text = question.question

        if (!question.imgUri.isNullOrEmpty()) {
            holder.questionImageView.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(question.imgUri)
                .into(holder.questionImageView)
        } else {
            holder.questionImageView.visibility = View.GONE
        }

        holder.nextButton.setOnClickListener {
            val userAnswer = holder.answerEditText.text.toString()
            val correctAnswer = questions[position].answer
            userAnswers[position] = userAnswer
            if (userAnswer.isBlank()) {
                Toast.makeText(holder.itemView.context, "Please enter an answer", Toast.LENGTH_SHORT).show()
            } else if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
                viewPager.currentItem = position + 1
            } else {
                Toast.makeText(holder.itemView.context, "Incorrect answer", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getUserAnswer(position: Int): String? {
        return userAnswers[position]
    }
    override fun getItemCount(): Int {
        return questions.size
    }
}