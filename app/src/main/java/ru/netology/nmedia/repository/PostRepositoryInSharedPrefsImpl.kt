package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryInSharedPrefsImpl (private val context: Context) : PostRepository {

    companion object{
        private val gson = Gson()
        private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
        private const val FILENAME = "posts.json"

    }

    private var posts = emptyList<Post>()
        set(value) {
            field = value
            sync()
        }
    private val data = MutableLiveData(posts)
    override fun get(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) {
                        post.likes - 1
                    } else {
                        post.likes + 1
                    }
                )
            } else {
                post
            }
        }
        data.value = posts
    }

    init {
        val file = context.filesDir.resolve(FILENAME)
        if (file.exists()){
            context.openFileInput(FILENAME).bufferedReader().use {
                posts = gson.fromJson (it, typeToken)
                data.value = posts
            }
        }
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    share = post.share + 1
                )
            } else {
                post
            }
        }
        data.value = posts
    }


    override fun cancel(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
        data.value = posts
    }

    override fun create(content: String) {
        posts = listOf(
            Post(
                id = generateNewId(),
                content = content,
                author = "Me",
                published = "Now"
            )
        ) + posts
        data.value = posts
    }

    override fun updateContent(id: Long, newContent: String) {
        posts = posts.map {
            if (it.id == id) it.copy(content = newContent) else it
        }
        data.value = posts
    }

    private fun sync(){
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    private fun generateNewId() = (posts.maxByOrNull { it.id }?.id ?: 0L) + 1L
}



