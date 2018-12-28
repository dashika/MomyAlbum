package cf.dashika.momyalbum.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cf.dashika.momyalbum.R
import cf.dashika.momyalbum.Model.Entity.Post
import cf.dashika.momyalbum.ViewModels.PostViewModel
import cf.dashika.momyalbum.databinding.ListItemRememberBinding

class AlbumAdapter : PagedListAdapter<Post, AlbumAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_remember, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumAdapter.ViewHolder, position: Int) {
        val post = getItem(position)
        if (post != null) {
            holder.bind(post)
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            //holder.clear()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Post>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldPost: Post,
                newPost: Post
            ): Boolean =
                oldPost.postId == newPost.postId

            override fun areContentsTheSame(
                oldPost: Post,
                newPost: Post
            ): Boolean =
                oldPost == newPost
        }
    }

    class ViewHolder(
        private val binding: ListItemRememberBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            with(binding) {
                viewModel = PostViewModel(
                    itemView.context,
                    post
                )
                executePendingBindings()
            }
        }
    }
}
