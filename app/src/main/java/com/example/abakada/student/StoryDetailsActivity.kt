package com.example.abakada.student

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.abakada.R
import com.example.abakada.databinding.ActivityStoryDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale


data class StoryPart(
    var imageUrl: Uri? = null,
    var text: String = ""
)

data class StoryData(
    val title: String = "",
    val parts: List<StoryPart> = emptyList()
)

class StoryDetailsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityStoryDetailsBinding
    private lateinit var tts: TextToSpeech
    private val handler = Handler(Looper.getMainLooper())
    private var currentHighlightSpan: BackgroundColorSpan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        val storyId = intent.getStringExtra("storyId")
        if (storyId != null) {
            fetchStoryData(storyId) { storyData ->
                binding.storyTitle.text = storyData.title

                val storyPartsContainer = binding.storyPartsContainer
                for (storyPart in storyData.parts) {
                    val imageView = ImageView(this)
                    imageView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        350
                    )
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.setPadding(0, 10, 0, 10)
                    Glide.with(this)
                        .load(storyPart.imageUrl)
                        .into(imageView)

                    storyPartsContainer.addView(imageView)

                    val textView = TextView(this, null, 0, R.style.TextStory)
                    textView.text = storyPart.text
                    textView.isClickable = true

                    textView.setOnClickListener {
                        speakOut(storyPart.text, textView) // Call speakOut on click
                    }

                    storyPartsContainer.addView(textView)
                }
            }
        } else {
            Toast.makeText(this, "Invalid story ID", Toast.LENGTH_SHORT).show()
        }
        binding.backButton.setOnClickListener { finish() }
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                for (voice in tts.voices) {
                    if (voice.name.contains("en-us-x-sfg#female_2-local")) {
                        tts.voice = voice
                        break
                    }
                }
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
    private fun speakOut(text: String, textView: TextView) {
        val spannableText = SpannableString(text)
        val highlightColor = Color.YELLOW
        val words = text.split(" ")
        var currentWordIndex = 0
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {

            }

            override fun onDone(utteranceId: String?) {
                runOnUiThread {
                    spannableText.removeSpan(BackgroundColorSpan(highlightColor))
                    textView.text = spannableText
                }
            }

            override fun onError(utteranceId: String?) {
            }

            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                runOnUiThread {
                    // Clear previous highlight
                    spannableText.removeSpan(ForegroundColorSpan(Color.YELLOW))

                    // Highlight the current word
                    if (currentWordIndex < words.size) {
                        val wordStart = spannableText.toString().indexOf(words[currentWordIndex])
                        val wordEnd = wordStart + words[currentWordIndex].length

                        spannableText.setSpan(
                            ForegroundColorSpan(Color.YELLOW),
                            wordStart,
                            wordEnd,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                        textView.text = spannableText
                        currentWordIndex++
                    }
                }
            }
        })
    }

    private fun fetchStoryData(storyId: String, callback: (StoryData) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("stories")
            .document(storyId)
            .get()
            .addOnSuccessListener { document ->
                val title = document.getString("title") ?: ""
                val parts = document.get("parts") as? List<Map<String, Any>> ?: emptyList()

                val storyParts = parts.map { partMap ->
                    val imageUrl = partMap["imageUrl"] as? String
                    val text = partMap["text"] as? String ?: ""
                    StoryPart(imageUrl = imageUrl?.let { Uri.parse(it) }, text = text)
                }

                callback(StoryData(title = title, parts = storyParts))
            }
            .addOnFailureListener { exception ->
                callback(StoryData())
            }
    }
    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}