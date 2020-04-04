package com.sohayb.covidinfo.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sohayb.covidinfo.R
import com.sohayb.covidinfo.Retrofit.RetroFit
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_country.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class ViewCountry : AppCompatActivity() {

    lateinit var webService : RetroFit
    lateinit var name : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_country)

        name = intent.getStringExtra("name")!!
        Picasso.get().load(intent.getStringExtra("flag")).into(Flag)
        NameOfViewCountry.text = name

        BTN1.text= intent.extras!!.get("totalCases").toString()
        BTN2.text= intent.extras!!.get("todayCases").toString()
        BTN3.text=intent.extras!!.get("totaldeaths").toString()
        BTN4.text= intent.extras!!.get("todayDeaths").toString()
        BTN5.text=intent.extras!!.get("recovered").toString()
        BTN6.text=intent.extras!!.get("active").toString()
        BTN7.text= intent.extras!!.get("critical").toString()
        BTN8.text= intent.extras!!.get("casesPerOneMillion").toString()
        BTN9.text=intent.extras!!.get("deathsPerOneMillion").toString()

        /*resfreshStuff.setOnClickListener {
            getAllCaractersAndFillRecycler(name)
        }*/
    }

    fun getAllCaractersAndFillRecycler(name : String) { // normally called from main at first
        CoroutineScope(Dispatchers.Main).launch { // or   GlobalScope.launch(Main)
            withContext(Dispatchers.IO) {
                webService.getApiInfo(this@ViewCountry).getCountryByName(name).await()
            }.also {
                BTN1.text= it.cases.toString()
                BTN2.text= it.todayCases.toString()
                BTN3.text=it.deaths.toString()
                BTN4.text= it.todayDeaths.toString()
                BTN5.text=it.recovered.toString()
                BTN6.text=it.active.toString()
                BTN7.text= it.critical.toString()
                BTN8.text=it.casesPerOneMillion.toString()
                BTN9.text=it.deathsPerOneMillion.toString()
            }

        }
    }


}