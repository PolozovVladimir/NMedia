package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory
import ru.netology.nmedia.repository.PostRepositoryInSharedPrefsImpl

val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",
    likedByMe = false,
    shareByMe = false,
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    val repository: PostRepository = PostRepositoryInSharedPrefsImpl(application)
    val data = repository.get()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun create(content: String) = repository.create(content)

    fun updateContent(id: Long, newContent: String) {
        viewModelScope.launch {
            repository.updateContent(id, newContent)
        }

    }
}
