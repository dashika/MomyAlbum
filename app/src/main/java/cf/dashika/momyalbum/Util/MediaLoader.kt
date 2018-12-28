package cf.dashika.momyalbum.Util

import android.widget.ImageView
import cf.dashika.momyalbum.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader


open class MediaLoader : AlbumLoader {

    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.path)
    }

    override fun load(imageView: ImageView, url: String) {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)


        Glide.with(imageView.context)
            .load(url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }


}