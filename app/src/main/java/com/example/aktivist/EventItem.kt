package com.example.aktivist
import android.os.Parcel
import android.os.Parcelable

data class EventItem(
    val title: String,
    val information: String,
    val imageResId: Int,
    val saved: Int,
    val date: Dates,
    val tur: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(Dates::class.java.classLoader) ?: Dates("", ""),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(information)
        parcel.writeInt(imageResId)
        parcel.writeInt(saved)
        parcel.writeParcelable(date, flags)
        parcel.writeString(tur)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<EventItem> {
        override fun createFromParcel(parcel: Parcel): EventItem {
            return EventItem(parcel)
        }

        override fun newArray(size: Int): Array<EventItem?> {
            return arrayOfNulls(size)
        }
    }
}

data class Dates(
    val localDate: String,
    val localTime: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(localDate)
        parcel.writeString(localTime)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Dates> {
        override fun createFromParcel(parcel: Parcel): Dates {
            return Dates(parcel)
        }

        override fun newArray(size: Int): Array<Dates?> {
            return arrayOfNulls(size)
        }
    }
}
