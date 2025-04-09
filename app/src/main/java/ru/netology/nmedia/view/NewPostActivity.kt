package ru.netology.nmedia.view

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.util.focusAndShowKeyboard

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getLongExtra("post_id", 0L)
        val content = intent.getStringExtra("content") ?: ""

        binding.content.setText(content)
        binding.content.focusAndShowKeyboard()

        binding.ok.setOnClickListener {
            val newContent = binding.content.text.toString()
            if (newContent.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                setResult(RESULT_OK, Intent().apply {
                    putExtra("post_id", postId)
                    putExtra("content", newContent)
                })
            }
            finish()
        }
    }
}


object NewPostContract : ActivityResultContract<Pair<Long, String>, Pair<Long, String>?>() {
    override fun createIntent(context: Context, input: Pair<Long, String>) =
        Intent(context, NewPostActivity::class.java).apply {
            putExtra("post_id", input.first)
            putExtra("content", input.second)
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Long, String>? {
        return if (resultCode == RESULT_OK) {
            intent?.run {
                getLongExtra("post_id", 0L) to getStringExtra("content").orEmpty()
            }
        }  else {
            null
        }
    }
}


