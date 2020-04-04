package com.sohayb.covidinfo.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CountryInfo(
    val _id: Int,
    val flag: String,
    val iso2: String?,
    val iso3: String?,
    val lat: Float,
    val long: Float
) : Parcelable