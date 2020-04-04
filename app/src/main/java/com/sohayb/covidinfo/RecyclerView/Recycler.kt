package com.sohayb.covidinfo.RecyclerView

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sohayb.covidinfo.Models.Country
import com.sohayb.covidinfo.R
import com.sohayb.covidinfo.View.ViewCountry
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.one_country_view.view.*

class Recycler(val list: ArrayList<Country>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        context = parent.context
        return CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.one_country_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CountryViewHolder -> {
              list.let { holder.Bind(list[position]) }

            }
        }
    }


    inner class CountryViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun Bind(country: Country) {

            Picasso.get().load(country.countryInfo.flag).into(itemView.CountryImage)
            itemView.NameOfCountry.text = country.country

            itemView.setOnClickListener {

                val destination = Intent(
                    context,
                    ViewCountry::class.java
                ).apply {

                    putExtra("name", country.country)
                    putExtra("flag", country.countryInfo.flag)
                    putExtra("totalCases", country.cases)
                    putExtra("todayCases", country.todayCases)
                    putExtra("totaldeaths", country.deaths)
                    putExtra("todayDeaths", country.todayDeaths)
                    putExtra("recovered",country.recovered)
                    putExtra("active",country.active)
                    putExtra("critical", country.critical)
                    putExtra("casesPerOneMillion",country.casesPerOneMillion)
                    putExtra("deathsPerOneMillion",country.deathsPerOneMillion)
                    putExtra("updated", country.updated)
                }

                (context as Activity).startActivity(destination)

            }


            itemView.setOnLongClickListener {

                true
            }
        }
    }
}