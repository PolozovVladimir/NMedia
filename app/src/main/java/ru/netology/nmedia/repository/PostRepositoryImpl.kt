package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll(): List<Post>  {
        TODO()
    }

    override fun likeById(id: Long) = dao.likedById(id)
    override fun shareById(id: Long) = dao.shareById(id)
    override fun removeById(id: Long) = dao.removeById(id)
    override fun save(post: Post) = dao.save(PostEntity.fromDto(post))
}