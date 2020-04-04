package com.sohayb.covidinfo.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Country(
    val active: Int,
    val cases: Int,
    val casesPerOneMillion: Float,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Int,
    val deaths: Int,
    val deathsPerOneMillion: Float,
    val recovered: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val updated: Long
) : Parcelable