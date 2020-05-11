package com.example.currencyexchange.network.fxdata.model

import android.os.Parcel
import android.os.Parcelable

data class FxData(
    val base: String?,
    val date: String?,
    val price: Double
) : Parcelable {

    lateinit var rates: Map<String, Double>

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble()
    ) {
        parcel.readMap(rates, parcel.javaClass.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeMap(rates)
        parcel.writeDouble(price)
        parcel.writeString(base)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FxData> {
        override fun createFromParcel(parcel: Parcel): FxData {
            return FxData(parcel)
        }

        override fun newArray(size: Int): Array<FxData?> {
            return arrayOfNulls(size)
        }
    }
}