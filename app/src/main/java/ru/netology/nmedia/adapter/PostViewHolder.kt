package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.view.CountCalculator

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener:OnLikeClicked,
    private val onShareListener:OnShareClicked,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post){
        with(binding) {
            shareQuantity.text = CountCalculator.calculator(post.share)

            share.setOnClickListener {
                shareQuantity.text = CountCalculator.calculator(post.share)
            }

        }
        with(binding) {
            likeQuantity.text = CountCalculator.calculator(post.likes)
            like.setImageResource(
                if (post.likedByMe) {
                    R.drawable.baseline_favorite_24
                } else {
                    R.drawable.baseline_favorite_border_24
                }

            )




            content.text = post.content
            author.text = post.author
            published.text = post.published

            like.setOnClickListener {
                onLikeListener(post)
            }
            share.setOnClickListener {
                onShareListener(post)
            }

        }
    }

    }
