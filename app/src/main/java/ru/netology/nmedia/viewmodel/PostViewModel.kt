package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

val empty = Post(
    id = 0,
    author = "",
    published = "",
    content = "",
    likedByMe = false,
    shareByMe = false,
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<List<Post>> = repository.get()
    val edited = MutableLiveData(empty)

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun saveContent(content: String) {
        edited.value?.let { editPost ->
            repository.save(editPost.copy(content = content))
        }

        edited.value = empty
    }
    fun cancelContent() {
        edited.value?.let {
            repository.cancel(it)
        }
        edited.value = empty
        }
    }
