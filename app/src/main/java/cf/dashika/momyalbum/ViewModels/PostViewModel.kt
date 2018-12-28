package cf.dashika.momyalbum.ViewModels

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import cf.dashika.momyalbum.Model.Entity.Post
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel(
    context: Context,
    post: Post
) : ViewModel() {

    private val topic = checkNotNull(post)
    private val dateFormat by lazy { SimpleDateFormat("MMM d, yyyy", Locale.US) }
    private val topicDateString by lazy { dateFormat.format(post.date) }

    val imageUrl = ObservableField<String>("https://cdn-images-1.medium.com/max/1000/1*3wgZOQYzFyN0IEw4SAj0Lw.jpeg")

}