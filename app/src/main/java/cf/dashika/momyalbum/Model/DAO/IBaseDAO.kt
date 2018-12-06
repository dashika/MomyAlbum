package cf.dashika.momyalbum.Model.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface IBaseDAO<T> {

    fun getAll(): LiveData<List<T>>

    fun getById(id: Long): LiveData<T>

    @Insert
    fun insert(item: T): Long

    @Delete
    fun delete(item: T)
}