package com.example.abakada.teacher.tabs.quizzes

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.abakada.R

class QuizItemsAdapter(private val quizListItemContainer: ViewGroup, private val quizType: String, private val startForResult: ActivityResultLauncher<Intent>) : RecyclerView.Adapter<QuizItemViewHolder>() {
    private val quizItems = mutableListOf<QuizQuestion>()
    var currentAdapterPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_item_layout, parent, false)
        val holder = QuizItemViewHolder(itemView)

        return holder
    }

    override fun onBindViewHolder(holder: QuizItemViewHolder, position: Int) {
        val quizItem = quizItems[position]
        holder.questionInput.setText(quizItem.question)
        holder.answerInput.setText(quizItem.answer)
        holder.removeItemButton.setOnClickListener {
            quizItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, quizItems.size)
        }
        if (!quizItem.imgUri.isNullOrEmpty()) {
            val imageUri = Uri.parse(quizItem.imgUri)
            holder.quizImage.setImageURI(imageUri)
        }
        holder.quizImage.setOnClickListener {
            currentAdapterPosition = holder.adapterPosition
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startForResult.launch(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizItems.size
    }
    fun updateImageUri(position: Int, imageUriString: String) {
        quizItems[position].imgUri = imageUriString
        notifyItemChanged(position)
    }


    fun getQuizData(): List<QuizQuestion> {
        val quizData = mutableListOf<QuizQuestion>()
        for (i in 0 until itemCount) {
            val holder = QuizItemViewHolder(quizListItemContainer.getChildAt(i))
            val question = holder.questionInput.text.toString()
            val answer = holder.answerInput.text.toString()
            val originalQuizItem = quizItems[i]

            // Create QuizQuestion with questionType
            val quizQuestion = QuizQuestion(
                questionType = if (quizType == "Matching") "MatchingType" else "FillInTheBlanks",
                question = question,
                answer = answer,
                imgUri = originalQuizItem.imgUri
            )
            quizData.add(quizQuestion)
        }
        return quizData
    }

    fun addQuizItem(quizItem: QuizQuestion) {
        quizItems.add(quizItem)
        notifyItemInserted(quizItems.size - 1)
    }
}