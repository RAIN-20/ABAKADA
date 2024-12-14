package com.example.abakada.student

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.databinding.ActivityModuleVideoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class ModuleVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModuleVideoBinding
    private var audioFocusRequested = false
    private lateinit var youTubePlayer: YouTubePlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModuleVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moduleName = intent.getStringExtra("moduleName")
        val moduleImg = intent.getStringExtra("moduleImg")
        val moduleId = intent.getStringExtra("moduleId")


        binding.moduleName.text = moduleName

        if (moduleId != null) {
            fetchModuleVideoFromFirebase(moduleId)
        } else {
            // Handle case where moduleId is null
            Log.e("ModuleVideoActivity", "moduleId is null")
        }
        Glide.with(this)
            .load(moduleImg)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(binding.moduleImage)

        binding.backButton.setOnClickListener {
            finish()
        }

    }
    private fun fetchModuleVideoFromFirebase(moduleId: String) {
        val db = FirebaseFirestore.getInstance()
        val modulesCollection = db.collection("modules")

        modulesCollection.document(moduleId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val moduleVideo = document.get("video") as? Map<*, *>
                    val videoLink = moduleVideo?.get("link") as? String
                    val videoDescription = moduleVideo?.get("description") as? String

                    binding.videoDescription.text = videoDescription ?: "" // Set video description

                    lifecycle.addObserver(binding.youtubePlayerView)
                    binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            this@ModuleVideoActivity.youTubePlayer = youTubePlayer
                            val videoId = extractVideoIdFromUrl(videoLink)
                            Log.d("ModuleVideoActivity", "Video ID: $videoId")
                            requestAudioFocus()
                            if (videoId != null) {
                                youTubePlayer.loadVideo(videoId.toString(), 0f)
                            } else {
                                youTubePlayer.loadVideo("zavYNAOazUs", 0f)
                            }
                        }
                    })
                } else {
                    // Handle case where document doesn't exist
                    Log.e("ModuleVideoActivity", "Module document not found")
                }
            }
            .addOnFailureListener { exception ->
                // Handle failures
                Log.e("ModuleVideoActivity", "Error fetching module video", exception)
            }
    }
    private fun requestAudioFocus() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(
            audioFocusChangeListener,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            audioFocusRequested = true
        }
    }

    private fun releaseAudioFocus() {
        if (audioFocusRequested) {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.abandonAudioFocus(audioFocusChangeListener)
            audioFocusRequested = false
        }
    }

    override fun onStop() {
        super.onStop()
        releaseAudioFocus() // Release audio focus when the activity is stopped
        youTubePlayer.pause() // Pause the YouTube player
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release() // Release the YouTube player view
    }

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                youTubePlayer.pause() // Pause playback
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                youTubePlayer.pause() // Pause playback
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                // Lower the volume (if needed)
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                youTubePlayer.play() // Resume playback
            }
        }
    }
    private fun extractVideoIdFromUrl(url: String?): String? {
        if (url == null || url.isEmpty()) {
            return null
        }

        val pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v%3D)([^#\\&\\?]*).*"
        val compiledPattern = Regex(pattern)
        val matcher = compiledPattern.find(url)

        return matcher?.value?.replace(pattern, "$1")
    }

}