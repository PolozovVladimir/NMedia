package ru.netology.nmedia.adapter

import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.view.CountCalculator

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post){
        with(binding) {
            share.text = CountCalculator.calculator(post.share)

            share.setOnClickListener {
                share.text = CountCalculator.calculator(post.share)
            }

        }
        with(binding) {
            like.text = CountCalculator.calculator(post.likes)
//            like.setImageResource(
//                if (post.likedByMe) {
//                    R.drawable.baseline_favorite_24
//                } else {
//                    R.drawable.baseline_favorite_border_24
//                }

//            )




            content.text = post.content
            author.text = post.author
            published.text = post.published
            like.isChecked = post.likedByMe


            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply{
                    inflate(R.menu.options_menu)
                    setOnMenuItemClickListener {menuItem->
                        when(menuItem.itemId){
                            R.id.remov -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit ->{
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }

                }.show()
            }

        }
    }

    }
