package ru.netology.nmedia.dto

data class Post(
    val id: Long = 0,
    val author: String = "",
    val published: String = "",
    val content: String = "",
    val likedByMe: Boolean = false,
    val shareByMe: Boolean = false,
    val likes: Int = 0,
    val share: Int = 0,
    val video: String? = null,
)