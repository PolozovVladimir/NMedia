package ru.netology.nmedia.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.view.CountCalculator

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.text = CountCalculator.calculator(post.likes)
            share.text = CountCalculator.calculator(post.share)
            like.isChecked = post.likedByMe

            if (!post.video.isNullOrBlank()) {
                videoContainer.visibility = View.VISIBLE
                videoThumbnail.setImageResource(R.drawable.video)
                videoDate.text = post.published
                videoTitle.text = post.content.take(50).plus("...")

                videoContainer.setOnClickListener { openVideo(post.video) }
                playButton.setOnClickListener { openVideo(post.video) }
            } else {
                videoContainer.visibility = View.GONE
            }

            like.setOnClickListener { onInteractionListener.onLike(post) }
            share.setOnClickListener { onInteractionListener.onShare(post) }
            menu.setOnClickListener { showPopupMenu(post) }
        }
    }

    private fun openVideo(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        try {
            itemView.context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                itemView.context,
                "Нет приложения для просмотра видео",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showPopupMenu(post: Post) {
        PopupMenu(binding.menu.context, binding.menu).apply {
            inflate(R.menu.options_menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.remov -> {
                        onInteractionListener.onRemove(post)
                        true
                    }
                    R.id.edit -> {
                        onInteractionListener.onEdit(post)
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}