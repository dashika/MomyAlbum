package cf.dashika.momyalbum.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cf.dashika.momyalbum.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.impl.OnItemClickListener
import com.yanzhenjie.album.util.AlbumUtils


class MediaAdapter(context: Context?, private val mItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mInflater: LayoutInflater

    private var mAlbumFiles: List<AlbumFile>? = null

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    fun notifyDataSetChanged(imagePathList: List<AlbumFile>) {
        this.mAlbumFiles = imagePathList
        super.notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val albumFile = mAlbumFiles!![position]
        return if (albumFile.mediaType == AlbumFile.TYPE_IMAGE) {
            AlbumFile.TYPE_IMAGE
        } else {
            AlbumFile.TYPE_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            AlbumFile.TYPE_IMAGE -> {
                return ImageViewHolder(
                    mInflater.inflate(R.layout.item_content_image, parent, false),
                    mItemClickListener
                )
            }
            AlbumFile.TYPE_VIDEO -> {
                return VideoViewHolder(
                    mInflater.inflate(R.layout.item_content_video, parent, false),
                    mItemClickListener
                )
            }
            else -> {
                throw AssertionError("This should not be the case.")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            AlbumFile.TYPE_IMAGE -> {
                (holder as ImageViewHolder).setData(mAlbumFiles!![position])
            }
            AlbumFile.TYPE_VIDEO -> {
                (holder as VideoViewHolder).setData(mAlbumFiles!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mAlbumFiles == null) 0 else mAlbumFiles!!.size
    }

    private class ImageViewHolder internal constructor(
        itemView: View,
        private val mItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mIvImage: ImageView

        init {
            this.mIvImage = itemView.findViewById(R.id.iv_album_content_image)
            itemView.setOnClickListener(this)
        }

        fun setData(albumFile: AlbumFile) {
            Album.getAlbumConfig().albumLoader.load(mIvImage, albumFile)
        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, adapterPosition)
            }
        }
    }

    private class VideoViewHolder internal constructor(
        itemView: View,
        private val mItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val mIvImage: ImageView
        private val mTvDuration: TextView

        init {
            this.mIvImage = itemView.findViewById(com.yanzhenjie.album.R.id.iv_album_content_image)
            this.mTvDuration = itemView.findViewById(com.yanzhenjie.album.R.id.tv_duration)
            itemView.setOnClickListener(this)
        }

        internal fun setData(albumFile: AlbumFile) {
            Album.getAlbumConfig().albumLoader.load(mIvImage, albumFile)
            mTvDuration.text = AlbumUtils.convertDuration(albumFile.duration)
        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, adapterPosition)
            }
        }
    }

}