package com.example.currencyexchange.network.fxhistory.model

import android.os.Parcel
import android.os.Parcelable

data class FxHistoryData(
    val base: String?
) : Parcelable {

    lateinit var rates: Map<String, Map<String, Double>>

    constructor(parcel: Parcel) : this(parcel.readString()) {
        parcel.readMap(rates, parcel.javaClass.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(base)
        parcel.writeMap(rates)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FxHistoryData> {
        override fun createFromParcel(parcel: Parcel): FxHistoryData {
            return FxHistoryData(parcel)
        }

        override fun newArray(size: Int): Array<FxHistoryData?> {
            return arrayOfNulls(size)
        }
    }
}