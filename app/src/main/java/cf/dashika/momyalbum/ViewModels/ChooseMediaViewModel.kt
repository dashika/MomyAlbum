package cf.dashika.momyalbum.ViewModels

import androidx.lifecycle.ViewModel
import com.yanzhenjie.album.AlbumFile

class ChooseMediaViewModel : ViewModel() {

    internal var albumFiles: ArrayList<AlbumFile>? = null

    fun albumFilesIsEmpty() = albumFiles == null || albumFiles!!.size == 0
    fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
