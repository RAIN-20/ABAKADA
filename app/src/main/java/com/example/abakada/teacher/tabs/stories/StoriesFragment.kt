package com.example.abakada.teacher.tabs.stories

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.databinding.FragmentStoriesBinding
import com.google.firebase.firestore.FirebaseFirestore


class StoriesFragment : Fragment() {
    private var _binding: FragmentStoriesBinding? = null
    private val binding get() = _binding!!
    private val storyList = mutableListOf<StoryData>()
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: StoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoriesBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        adapter = StoryAdapter()
        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecyclerView.adapter = adapter
        fetchStoriesFromFirebase()
        return binding.root
    }


    private fun fetchStoriesFromFirebase() {
        db.collection("stories")
            .get()
            .addOnSuccessListener { documents ->
                storyList.clear()
                for (document in documents) {
                    val storyTitle = document.getString("title")
                    val storyId = document.id
                    val storyData = StoryData(id = storyId, title = storyTitle!!) // Create StoryData object
                    Log.d(TAG, "Story Title: $storyTitle")
                    storyList.add(storyData)
                }
                adapter.submitList(storyList.toList())
                if (storyList.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.storiesRecyclerView.visibility = View.GONE
                    Log.d(TAG, "No stories found")
                } else {
                    binding.emptyView.visibility = View.GONE
                    binding.storiesRecyclerView.visibility = View.VISIBLE
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "No asdasd found")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "Error fetching stories", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}