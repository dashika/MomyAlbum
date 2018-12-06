package cf.dashika.momyalbum.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.Model.Entity.Topic
import cf.dashika.momyalbum.ViewModels.TopicViewModel
import cf.dashika.momyalbum.databinding.ListItemRememberBinding

class AlbumAdapter : PagedListAdapter<Topic, AlbumAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_remember, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumAdapter.ViewHolder, position: Int) {
        val topic = getItem(position)
        if (topic != null) {
            holder.bind(topic)
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            //holder.clear()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Topic>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldTopic: Topic,
                newTopic: Topic
            ): Boolean =
                oldTopic.topicId == newTopic.topicId

            override fun areContentsTheSame(
                oldTopic: Topic,
                newTopic: Topic
            ): Boolean =
                oldTopic == newTopic
        }
    }

    class ViewHolder(
        private val binding: ListItemRememberBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic) {
            with(binding) {
                viewModel = TopicViewModel(
                    itemView.context,
                    topic
                )
                executePendingBindings()
            }
        }
    }
}
