package cf.dashika.momyalbum.Model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "babies",
    foreignKeys = [androidx.room.ForeignKey(
        entity = Mamy::class,
        parentColumns = ["id"],
        childColumns = ["mamy_id"]
    )],
    indices = [Index("mamy_id")]
)
data class Baby(

    @ColumnInfo(name = "mamy_id") val mamyId: Long,
    val name: String,
    val birthday: Long,
    val avatar: String
) : BaseModel() {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var babyId: Long = 0
    lateinit var city: String
    override fun toString() = name
}