package ru.netology.nmedia.view

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

        val post = intent.getParcelableExtra<Post>("post") ?: Post()
        binding.content.setText(post.content)
        binding.content.focusAndShowKeyboard()

        binding.ok.setOnClickListener {
            val content = binding.content.text.toString()
            if (content.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val resultPost = post.copy(content = content)
                setResult(RESULT_OK, Intent().apply {
                    putExtra("post", resultPost)
                })
            }
            finish()
        }
    }
}


object NewPostContract : ActivityResultContract<Post, Post?>(){

    override fun createIntent(context: Context, input: Post) =
        Intent(context, NewPostActivity::class.java).apply {
            putExtra("post", input)
        }

    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.getParcelableExtra<Post>("post")
        }


