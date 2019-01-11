package cf.dashika.momyalbum.Model.Repository

import cf.dashika.momyalbum.Model.DAO.IMamyDAO
import cf.dashika.momyalbum.Model.Entity.Mamy
import cf.dashika.momyalbum.Util.runOnIoThread

class MamyRepository private constructor(private val mamyDao: IMamyDAO) {

    fun createMamy(name: String) {
        runOnIoThread {
            val mamy = Mamy(name)
            mamyDao.insert(mamy)
        }
    }

    fun removeMamy(mamy: Mamy) {
        runOnIoThread {
            mamyDao.delete(mamy)
        }
    }

    fun getBaby(babyId: Long) =
        mamyDao.getBaby(babyId)

    fun getMamies() = mamyDao.getAll()

    fun getMamyAndBabies() = mamyDao.getMamyAndBabies()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: MamyRepository? = null

        fun getInstance(mamyDao: IMamyDAO) =
            instance ?: synchronized(this) {
                instance ?: MamyRepository(mamyDao).also { instance = it }
            }
    }
}