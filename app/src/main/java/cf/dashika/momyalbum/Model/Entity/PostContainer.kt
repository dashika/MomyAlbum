package cf.dashika.momyalbum.Model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts_container",
    foreignKeys = [androidx.room.ForeignKey(
        entity = Post::class,
        parentColumns = ["id"],
        childColumns = ["post_id"]
    )],
    indices = [Index("post_id")]
)
data class PostContainer(
    @ColumnInfo(name = "post_id") val postId: Long,
    var url:String
) : BaseModel() {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var postContainerId: Long = 0
}