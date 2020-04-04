package com.sohayb.covidinfo

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sohayb.covidinfo.Models.Country
import com.sohayb.covidinfo.Retrofit.RetroFit
import com.sohayb.covidinfo.View.ListCountriesActivity
import com.sohayb.covidinfo.View.ViewCountry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/Provicali-w116n.ttf")

        //val list = intent!!.getParcelableArrayListExtra<Country>("countriesList")

        var list = intent.getParcelableArrayListExtra<Country>("countriesList")
        val tabCountries: ArrayList<String> = ArrayList()

        for (element in list!!) {
            tabCountries.add(element.country)
        }

        //val list = intent.getParcelableArrayListExtra<Country>("countriesList")
        //WelcomeTV.typeface = typeface
        //buttonAllPays.typeface = typeface
        //buttonRandom.typeface = typeface

        buttonAllPays.setOnClickListener {

            Intent(this@MainActivity, ListCountriesActivity::class.java).let {

                it.putParcelableArrayListExtra(
                    "countriesList",
                    list
                )
                startActivity(it)
            }
        }


        buttonRandom.setOnClickListener {
            val randomValue = Random.nextInt(list.size)
            getInfoForOneCountry(tabCountries[randomValue])

        }
    }


    fun getInfoForOneCountry(nameCountry: String) {

        val webservice = RetroFit()

        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {

                webservice.getApiInfo(this@MainActivity).getCountryByName(nameCountry).await()

            }.also { country ->
                val destination = Intent(this@MainActivity, ViewCountry::class.java).apply {

                    putExtra("name", country.country)
                    putExtra("flag", country.countryInfo.flag)
                    putExtra("totalCases", country.cases)
                    putExtra("todayCases", country.todayCases)
                    putExtra("totaldeaths", country.deaths)
                    putExtra("todayDeaths", country.todayDeaths)
                    putExtra("recovered", country.recovered)
                    putExtra("active", country.active)
                    putExtra("critical", country.critical)
                    putExtra("casesPerOneMillion", country.casesPerOneMillion)
                    putExtra("deathsPerOneMillion", country.deathsPerOneMillion)
                    putExtra("updated", country.updated)


                }
                startActivity(destination)
            }
        }


    }
}