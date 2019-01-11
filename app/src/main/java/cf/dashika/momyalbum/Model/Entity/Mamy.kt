package cf.dashika.momyalbum.Model.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "mamy"
)
data class Mamy(
    val name: String
) : BaseModel(), Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mamyId: Long = 0

    var birthday: Long=0
    lateinit var city: String
    lateinit var avatar: String

    constructor(parcel: Parcel) : this(name = parcel.readString()) {
        mamyId = parcel.readLong()
        birthday = parcel.readLong()
        city = parcel.readString()
        avatar = parcel.readString()
    }

    override fun toString() = name
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(mamyId)
        parcel.writeLong(birthday)
        parcel.writeString(city)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mamy> {
        override fun createFromParcel(parcel: Parcel): Mamy {
            return Mamy(parcel)
        }

        override fun newArray(size: Int): Array<Mamy?> {
            return arrayOfNulls(size)
        }
    }
}