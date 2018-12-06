package cf.dashika.momyalbum.ViewModels

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import cf.dashika.momyalbum.Model.Entity.Topic
import java.text.SimpleDateFormat
import java.util.*

class TopicViewModel  (context: Context,
                       topics: Topic
) : ViewModel() {

    private val topic = checkNotNull(topics)
    private val dateFormat by lazy { SimpleDateFormat("MMM d, yyyy", Locale.US) }
    private val topicDateString by lazy { dateFormat.format(topics.date) }

    val imageUrl = ObservableField<String>("https://cdn-images-1.medium.com/max/1000/1*3wgZOQYzFyN0IEw4SAj0Lw.jpeg")

}