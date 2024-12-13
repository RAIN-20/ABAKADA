package com.example.abakada.student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.R
import com.example.abakada.databinding.ActivityStoryBookBinding
import com.google.firebase.firestore.FirebaseFirestore

class StoryBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBookBinding
    private val storyBooks = mutableListOf<StoryBook>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBookBinding.inflate(layoutInflater) // Inflate layout
        setContentView(binding.root)
        enableEdgeToEdge()
        fetchStoryBooks()
        binding.backButton.setOnClickListener {
            finish()
        }
    }
    private fun fetchStoryBooks() {
        val db = FirebaseFirestore.getInstance()
        val storyBooksCollection = db.collection("stories")

        storyBooksCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val title = document.getString("title")
                    val documentId = document.id // Get document ID
                    storyBooks.add(StoryBook(title!!, documentId)) // Pass documentId to StoryBook
                }

                val adapter = StoryBookAdapter(storyBooks) { documentId ->
                    val intent = Intent(this, StoryDetailsActivity::class.java)
                    intent.putExtra("storyId", documentId)
                    startActivity(intent)
                }
                binding.storyBooksRecyclerView.adapter = adapter
                binding.storyBooksRecyclerView.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                Log.e("StoryBookActivity", "Error fetching story books", exception)
            }
    }
}