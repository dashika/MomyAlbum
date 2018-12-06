package cf.dashika.momyalbum.Model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "mamy"
)
data class Mamy(
    val name: String
) : BaseModel() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mamyId: Long = 0

    var birthday: Long=0
    lateinit var city: String
    lateinit var avatar: String

    override fun toString() = name
}