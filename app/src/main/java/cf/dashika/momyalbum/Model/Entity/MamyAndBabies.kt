package cf.dashika.momyalbum.Model.Entity

import androidx.room.Embedded
import androidx.room.Relation

class MamyAndBabies {
    @Embedded
    var mamy: Mamy? = null

    @Relation(parentColumn = "id", entityColumn = "id_mamy")
    var babies: List<Baby> = arrayListOf()
}