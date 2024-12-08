package com.example.abakada.teacher.tabs.stories

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abakada.R
import com.example.abakada.databinding.FragmentStoriesBinding
import com.google.firebase.firestore.FirebaseFirestore



class StoriesFragment : Fragment() {
    private var _binding: FragmentStoriesBinding? = null
    private val binding get() = _binding!!
    private val storyList = mutableListOf<StoryData>()
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: StoryAdapter
    private var actionMode: ActionMode? = null
    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.contextual_menu, menu)
            // Change add button to delete button
            binding.addArticlesButton.setImageResource(R.drawable.ic_delete)
            // Set click listener for delete button in action mode
            binding.addArticlesButton.setOnClickListener {
                deleteSelectedItems()
                mode.finish()
            }
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.delete -> {
                    deleteSelectedItems()
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
            // Change delete button back to add button
            binding.addArticlesButton.setImageResource(R.drawable.ic_add)
            adapter.selectedItems.clear()
            adapter.notifyDataSetChanged()
            // Reset click listener to original functionality
            binding.addArticlesButton.setOnClickListener {
                val intent = Intent(requireContext(), AddStoriesActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoriesBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        adapter = StoryAdapter(actionModeCallback)
        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.storiesRecyclerView.adapter = adapter

        fetchStoriesFromFirebase()
        binding.addArticlesButton.setOnClickListener {
            val intent = Intent(requireContext(), AddStoriesActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    private fun deleteSelectedItems() {
        val selectedStories = adapter.selectedItems.toList()
        for (story in selectedStories) {
            db.collection("stories").document(story.id).delete()
                .addOnSuccessListener {
                    storyList.remove(story)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "Story deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error deleting story: ${story.id}", e)
                    Toast.makeText(requireContext(), "Error deleting story", Toast.LENGTH_SHORT).show()
                }
        }
        adapter.selectedItems.clear()
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