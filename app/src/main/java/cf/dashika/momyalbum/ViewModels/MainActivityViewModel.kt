package cf.dashika.momyalbum.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cf.dashika.momyalbum.Model.Entity.MamyAndBabies
import cf.dashika.momyalbum.Model.Repository.MamyRepository

class MainActivityViewModel internal constructor(
    mamyRepository: MamyRepository
) : ViewModel() {

    val babies = mamyRepository.getMamyAndBabies()

    val mamyAndBabies: LiveData<List<MamyAndBabies>> =
        Transformations.map(mamyRepository.getMamyAndBabies()) { baby ->
            baby.filter { it.babies.isNotEmpty() }
        }
}