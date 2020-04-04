package com.sohayb.covidinfo.Retrofit

import com.sohayb.covidinfo.Models.Country
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {


    @GET("countries")
    fun GetAllCountries() : Call<ArrayList<Country>>


    @GET("countries/{name}")
    fun getCountryByName(@Path("name") name: String): Call<Country>
}