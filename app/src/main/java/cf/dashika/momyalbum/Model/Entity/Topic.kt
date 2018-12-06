package cf.dashika.momyalbum.Model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "topics",
    foreignKeys = [androidx.room.ForeignKey(
        entity = Baby::class,
        parentColumns = ["id"],
        childColumns = ["id_baby"]
    )],
    indices = [Index("id_baby")]
)
data class Topic(
    @ColumnInfo(name = "id_baby") val babyId: Long,
    val description: String,
    val date: Long,
    val likes: Int
) : BaseModel() {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var topicId: Long = 0
}