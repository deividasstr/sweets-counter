package com.deividasstr.ui.base.models

import android.os.Parcel
import android.os.Parcelable
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.ui.features.sweetdetails.SweetRating

data class SweetUi(
    val id: Long,
    val name: String,
    val calsPer100: Long,
    val fatG: Double,
    val carbsG: Double,
    val sugarG: Double,
    val proteinG: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble())

    constructor(sweet: Sweet) : this(
        sweet.id, sweet.name, sweet.calsPer100,
        sweet.fatG, sweet.carbsG, sweet.sugarG, sweet.proteinG
    )

    fun sweetRating(): SweetRating {
        return when {
            calsPer100 >= 400 || sugarG >= 30 -> SweetRating.BAD
            calsPer100 >= 200 || sugarG >= 10 -> SweetRating.AVERAGE
            else -> SweetRating.GOOD
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeLong(calsPer100)
        parcel.writeDouble(fatG)
        parcel.writeDouble(carbsG)
        parcel.writeDouble(sugarG)
        parcel.writeDouble(proteinG)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SweetUi> {
        override fun createFromParcel(parcel: Parcel): SweetUi {
            return SweetUi(parcel)
        }

        override fun newArray(size: Int): Array<SweetUi?> {
            return arrayOfNulls(size)
        }
    }
}

fun SweetUi.toSweet(): Sweet {
    return Sweet(id, name, calsPer100, fatG, carbsG, sugarG, proteinG)
}
