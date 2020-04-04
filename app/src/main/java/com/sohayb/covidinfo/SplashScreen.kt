package com.sohayb.covidinfo

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sohayb.covidinfo.Retrofit.RetroFit
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.await


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    //@SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

            CoroutineScope(Main).launch {

                GetInfoAndSendToMainActivity()
            }
    }


     suspend fun GetInfoAndSendToMainActivity() {
        delay(2000)
         var   webservice = RetroFit()

        CoroutineScope(Main).launch {

            withContext(Dispatchers.IO) {

                webservice.getApiInfo(this@SplashScreen).GetAllCountries().await()

            }.also { array ->

                for(l in array){
                    Log.i("country",l.country)
                }
                Intent(this@SplashScreen,MainActivity::class.java).also {

                    it.putParcelableArrayListExtra("countriesList" , array)
                    startActivity(it)
                }
            }
        }
    }


}