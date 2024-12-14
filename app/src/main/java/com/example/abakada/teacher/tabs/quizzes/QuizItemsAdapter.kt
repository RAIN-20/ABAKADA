package com.example.abakada.teacher.tabs.quizzes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abakada.R

class QuizItemsAdapter(private val quizListItemContainer: ViewGroup, private val quizType: String) : RecyclerView.Adapter<QuizItemViewHolder>() {
    private val quizItems = mutableListOf<QuizQuestion>()
    private val imageUrls = mutableMapOf<Int, String>()
    companion object {
        const val REQUEST_IMAGE_URL = 1001 // Define the request code here
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_item_layout, parent, false)
        val holder = QuizItemViewHolder(itemView)

//        holder.answerInput.visibility = if (quizType == "Matching") View.VISIBLE else View.GONE
//        holder.answerLabel.visibility = if (quizType == "Matching") View.VISIBLE else View.GONE

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
        quizItem.imgUri?.let { imageUri ->
            Glide.with(holder.itemView.context)
                .load(imageUri)
                .into(holder.quizImage)
        }
        val currentPosition = holder.adapterPosition
        holder.quizImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra("position", currentPosition)
            }
            (holder.itemView.context as? Activity)?.startActivityForResult(intent, REQUEST_IMAGE_URL)
        }
    }
    fun updateImageUri(position: Int, imageUriString: String) {
        val imageUri = Uri.parse(imageUriString) // Convert to Uri
        quizItems[position].imgUri = imageUri // Update imgUri directly
        notifyItemChanged(position) // Notify adapter of change
    }

    override fun getItemCount(): Int {
        return quizItems.size
    }


    fun getQuizData(): List<QuizQuestion> {
        val quizData = mutableListOf<QuizQuestion>()
        for (i in 0 until itemCount) {
            val holder = QuizItemViewHolder(quizListItemContainer.getChildAt(i))
            val question = holder.questionInput.text.toString()
            val answer = holder.answerInput.text.toString()

            if (quizType == "Matching" ) {
                quizData.add(QuizQuestion.MatchingType(question, answer))
            } else {
                quizData.add(QuizQuestion.FillInTheBlanks(question, answer))
            }
        }
        return quizData
    }

    fun addQuizItem(quizItem: QuizQuestion) {
        quizItems.add(quizItem)
        notifyItemInserted(quizItems.size - 1)
    }
}