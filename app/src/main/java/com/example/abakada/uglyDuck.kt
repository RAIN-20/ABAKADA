package com.example.abakada

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class uglyDuck : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var story1: TextView
    private lateinit var story2: TextView
    private lateinit var story3: TextView
    private lateinit var story4: TextView
    private lateinit var story5: TextView
    private lateinit var story6: TextView
    private lateinit var story7: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ugly_duck)


        // Initialize TextViews
        story1 = findViewById(R.id.part1)
        story2 = findViewById(R.id.part2)
        story3 = findViewById(R.id.part3)
        story4 = findViewById(R.id.part4)
        story5 = findViewById(R.id.part5)
        story6 = findViewById(R.id.part6)
        story7 = findViewById(R.id.part7)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US  // Set the language to English (US)
            }
        }

        story1.setOnClickListener {
            val text = "Once upon a time a Mama Duck sat on her nest. 'These eggs are taking a long time to hatch,' she thought. " +
                    "But a Mama duck must keep her eggs warm until they do hatch. At last, the eggs began to crack. Pretty yellow ducklings came out of their shells. " +
                    "They shook their little wings and said, 'Quack, quack!' Mama Duck was very happy. They were all so cute. "
            speakAndHighlightText(text, story1)
        }

        story2.setOnClickListener {
            val text = "Mama Duck said, 'Come on ducklings, off we go to the pond for your first swim.' Then she counted them: one, two, three, four, five. " +
                    "'Oh dear!' she thought, 'I should have six ducklings!' " +
                    "There was one big egg still in the nest. 'Oh well,' thought Mama Duck, 'I'd better sit on my nest again and wait.' " +
                    "The next day, the big egg hatched and out came a baby boy duckling. He was much bigger than the others. He was dark grey and not yellow. " +
                    "And he had longer legs than the others. One of the yellow ducklings pointed at him, 'He doesn't look like one of us!' " +
                    "'Such an ugly duckling!' said another. " +
                    "'That is not a nice thing to say,' said Mama Duck, 'he hatched from the same nest as you. Now, off to the pond for your very first swim.'"
            speakAndHighlightText(text, story2)
        }

        story3.setOnClickListener {
            val text = "The Ugly Duckling did not know why the other ducklings were being so horrible to him. Each yellow duckling jumped in the pond and swam behind Mama Duck. " +
                    "The Ugly Duckling jumped in and started to paddle, too. 'He's a good swimmer,' Mama Duck thought. " +
                    "The Ugly Duckling tried to play with his brothers and sisters. But they yelled, 'Go away, you're ugly and grey. And your legs are too long!' " +
                    "One day, one of the yellow ducklings said to the Ugly Duckling, 'We don't like you and wish you would go away!' " +
                    "Then they all quacked, 'Go away, go away, go away!!' The Ugly Duckling was sad. 'Maybe I should just go,' he thought to himself. " +
                    "That night, the Ugly Duckling flew away to the other side of the pond. He met two grown-up ducks. 'Can I stay with you, please?' asked the Ugly Duckling. " +
                    "'All right,' said one of the ducks, 'but just keep out of our way.' Suddenly a big hungry dog started chasing the two ducks. They flew up in the air, and some feathers fell down on the ground. " +
                    "The poor Ugly Duckling was frightened. The dog sniffed and sniffed at the Ugly Duckling then turned away. 'I am so ugly that even a big hungry dog doesn't want me,' thought the Ugly Duckling with a tear in his eye."
            speakAndHighlightText(text, story3)
        }

        story4.setOnClickListener {
            val text = "Soon he found a new pond. He looked up and saw a flock of big white birds flying by. They were the most beautiful birds he had ever seen! " +
                    "He stayed at that pond all by himself for a long time. " +
                    "Then winter came, and the cold wind and the dark clouds made the poor Ugly Duckling very sad and lonely. " +
                    "He had to go into the cold pond to fish, but it was getting hard to swim. The lake turned to ice. " +
                    "One day he felt the water freezing around him and trapping him in the water."
            speakAndHighlightText(text, story4)
        }

        story5.setOnClickListener {
            val text = "“Oh, I’m so tired!” he thought, paddling hard. The ice got thicker and thicker. " +
                    "Suddenly two big hands picked him up. " +
                    "“You poor little thing!” said a farmer. He took the Ugly Duckling back to his warm farmhouse. " +
                    "For the rest of that winter, the kind farmer looked after the Ugly Duckling."
            speakAndHighlightText(text, story5)
        }

        story6.setOnClickListener {
            val text = "Then spring came. " +
                    "“Time for you to go to the pond to swim again, that’s what you were born to do,” said the farmer. " +
                    "He took the young bird back to the pond where he had found him and set him down on the water. " +
                    "“I feel so strong now!” said the young bird to himself, and flapped his wings. " +
                    "Just then a flock of those same beautiful birds he had seen before landed on the water. " +
                    "“I know I’m ugly,” he said to them, “so I will go away.” A great big tear rolled down his cheek. " +
                    "But when he opened his eyes, he saw a reflection in the water. It looked just like one of those beautiful white birds. " +
                    "Why was it so close to him? He jumped back. And the reflection jumped back, too. " +
                    "He stretched out his long neck and the reflection stretched its neck, too. " +
                    "“We wish you would stay,” said one of the beautiful birds. " +
                    "“Yes, stay with us!” said another, “we can all be great friends.”"
            speakAndHighlightText(text, story6)
        }

        story7.setOnClickListener {
            val text = "Then he knew just what had happened! He wasn’t an ugly grey bird with funny long legs anymore. " +
                    "He was a beautiful swan after all! " +
                    "Suddenly, all the other swans flapped their wings and took off into the sky. " +
                    "“Come with us,” one called back. " +
                    "So he flapped his beautiful white, swanny wings as fast as they could go and joined his new friends. " +
                    "The End"
            speakAndHighlightText(text, story7)
        }





    }

    private fun speakAndHighlightText(text: String, textView: TextView) {
        val words = text.split(" ")
        val spannableString = SpannableString(text)
        textView.text = spannableString

        var currentWordIndex = 0

        textToSpeech.setSpeechRate(1.0f) // Normal speech rate
        textToSpeech.setPitch(1.0f)      // Normal pitch

        // Function to highlight words within a sentence
        fun highlightWords() {
            if (currentWordIndex < words.size) {
                val start = spannableString.toString().indexOf(words[currentWordIndex])
                val end = start + words[currentWordIndex].length

                // Highlight the current word
                spannableString.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                textView.text = spannableString

                currentWordIndex++
                textView.postDelayed({ highlightWords() }, 400) // Highlight next word
            }
        }

        // Speak text fluently with highlights
        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "ttsID"
        )

        highlightWords() // Start highlighting words
    }


    override fun onDestroy() {
        super.onDestroy()
        // Release TextToSpeech resources
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
}