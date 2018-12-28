package cf.dashika.momyalbum.Model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "posts",
    foreignKeys = [androidx.room.ForeignKey(
        entity = Baby::class,
        parentColumns = ["id"],
        childColumns = ["baby_id"]
    )],
    indices = [Index("baby_id")]
)
data class Post(
    @ColumnInfo(name = "baby_id") val babyId: Long,
    val description: String,
    val date: Long,
    val likes: Int
) : BaseModel() {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var postId: Long = 0
}