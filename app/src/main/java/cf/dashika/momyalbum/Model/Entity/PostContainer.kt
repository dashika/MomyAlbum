package cf.dashika.momyalbum.Model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "topics",
    foreignKeys = [androidx.room.ForeignKey(
        entity = Topic::class,
        parentColumns = ["id"],
        childColumns = ["id_topic"]
    )],
    indices = [Index("id_topic")]
)
data class TopicURL(
    @ColumnInfo(name = "id_topic") val topicId: Long,
    var url:String
) : BaseModel() {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var topicURLId: Long = 0
}