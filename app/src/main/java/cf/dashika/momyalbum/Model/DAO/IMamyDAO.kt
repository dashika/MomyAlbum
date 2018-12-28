package cf.dashika.momyalbum.Model.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import cf.dashika.momyalbum.Model.Entity.Baby
import cf.dashika.momyalbum.Model.Entity.Mamy
import cf.dashika.momyalbum.Model.Entity.MamyAndBabies

@Dao
interface IMamyDAO : IBaseDAO<Mamy> {
    @Query("SELECT * FROM mamy")
    override fun getAll(): LiveData<List<Mamy>>

    @Query("SELECT * FROM mamy WHERE id = :mamyId")
    override fun getById(mamyId: Long): LiveData<Mamy>

    @Query("SELECT * FROM babies WHERE mamy_id = :mamyId")
    fun getBaby(mamyId: Long): LiveData<Baby>

    @Transaction
    @Query("SELECT * FROM babies")
    fun getMamyAndBabies(): LiveData<List<MamyAndBabies>>

}