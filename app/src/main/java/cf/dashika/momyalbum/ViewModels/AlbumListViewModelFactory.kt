package cf.dashika.momyalbum.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cf.dashika.momyalbum.Model.Repository.MamyRepository

class AlbumListViewModelFactory (
    private val repository: MamyRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlbumViewModel(repository) as T
    }
}