package nmedia.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository

class PostRepositoryInMemoryImpl(application: Application) : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Погода в Париже",
            content = "Сегодня облачно, без осадков. Сегодня облачно, без осадков. Сегодня облачно, без осадков. Сегодня облачно, без осадков. Сегодня облачно, без осадков. Сегодня облачно, без осадков. Сегодня облачно, без осадков. ",
            published = "28 февраля в 10:00",
            likedByMe = false,
            likes = 234,
        ),
        Post(
            id = nextId++,
            author = "Погода в Париже",
            content = "Сегодня будет легкое похолоданеие. Сегодня будет легкое похолоданеие. Сегодня будет легкое похолоданеие. Сегодня будет легкое похолоданеие. Сегодня будет легкое похолоданеие. Сегодня будет легкое похолоданеие. ",
            published = "27 февраля в 10:00",
            likedByMe = false,
            likes = 342,
        ),
        Post(
            id = nextId++,
            author = "Погода в Париже",
            content = "На сегодня ожидается снегопад. На сегодня ожидается снегопад. На сегодня ожидается снегопад. На сегодня ожидается снегопад. На сегодня ожидается снегопад. На сегодня ожидается снегопад. На сегодня ожидается снегопад. ",
            published = "26 февраля в 10:00",
            likedByMe = false,
            likes = 135,
        ),
    ).reversed()

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Погода в Париже",
                    likedByMe = false,
                    published = "Сейчас"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun shar(id: Long) {
        TODO("Not yet implemented")
    }
}