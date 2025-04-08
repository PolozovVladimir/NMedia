package ru.netology.nmedia.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.util.AndroidUtils
import ru.netology.util.focusAndShowKeyboard

class MainActivity : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            val binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val viewModel: PostViewModel by viewModels()

            val postLauncher = registerForActivityResult(NewPostContract) { resultPost ->
                resultPost?.let { post ->
                    if (post.id == 0L) {
                        viewModel.create(post)
                    } else {
                        viewModel.update(post)
                    }
                }
            }

            binding.add.setOnClickListener{
                postLauncher.launch(Post())
            }
            val adapter = PostAdapter(object : OnInteractionListener {


                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onEdit(post: Post){
                    postLauncher.launch(post)
                }

                override fun onShare(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val chooser =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(chooser)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }


            }
            )
            binding.main.adapter = adapter

            viewModel.data.observe(this) { posts ->
                val newPost = adapter.currentList.size < posts.size
                adapter.submitList(posts) {
                    if (newPost) {
                        binding.main.smoothScrollToPosition(0)
                    }

                }
            }

        }

    }




