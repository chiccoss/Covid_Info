package com.sohayb.covidinfo.Retrofit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sohayb.covidinfo.ErrorClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException

class RetroFit {

        val BASE_URL = "https://corona.lmao.ninja/"
    private  var retrofit : Retrofit


    val okHttpClient = OkHttpClient.Builder().build()
    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }


    fun getApiInfo(context: Context) : ApiInterface {

        try {
            return retrofit.create(ApiInterface::class.java)
        }catch (e : UnknownHostException){

            Log.i("country",e.message.toString())
            val destination = Intent(context, ErrorClass::class.java)
            (context as Activity).startActivity(destination)
        }catch (e : Exception){
            Log.i("country",e.message.toString())
        }catch (e :  ConnectException){
            Log.i("country",e.message.toString())
        }

        return retrofit.create(ApiInterface::class.java)



    }

}