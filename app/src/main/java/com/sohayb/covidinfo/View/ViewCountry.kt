package com.sohayb.covidinfo.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sohayb.covidinfo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_country.*

class ViewCountry : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_country)

        Picasso.get().load(intent.getStringExtra("flag")).into(Flag)
        NameOfViewCountry.text = intent.getStringExtra("name")

        BTN1.text= intent.extras!!.get("totalCases").toString()
        BTN2.text= intent.extras!!.get("todayCases").toString()
        BTN3.text=intent.extras!!.get("totaldeaths").toString()
        BTN4.text= intent.extras!!.get("todayDeaths").toString()
        BTN5.text=intent.extras!!.get("recovered").toString()
        BTN6.text=intent.extras!!.get("active").toString()
        BTN7.text= intent.extras!!.get("critical").toString()
        BTN8.text= intent.extras!!.get("casesPerOneMillion").toString()
        BTN9.text=intent.extras!!.get("deathsPerOneMillion").toString()


    }


}