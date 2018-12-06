package cf.dashika.momyalbum.DI

import android.content.Context
import cf.dashika.momyalbum.Model.AppDatabase
import cf.dashika.momyalbum.Model.Repository.MamyRepository
import cf.dashika.momyalbum.ViewModels.AlbumListViewModelFactory

object InjectorUtils {
    fun mamyListViewModelFactory(
        context: Context
    ): AlbumListViewModelFactory {
        val repository = getMamyRepository(context)
        return AlbumListViewModelFactory(repository)
    }

    private fun getMamyRepository(context: Context): MamyRepository {
        return MamyRepository.getInstance(
            AppDatabase.getInstance(context).mamyDao())
    }
}