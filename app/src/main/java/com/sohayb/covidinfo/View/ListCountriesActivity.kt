package com.sohayb.covidinfo.View

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohayb.covidinfo.Models.Country
import com.sohayb.covidinfo.R
import com.sohayb.covidinfo.RecyclerView.Recycler
import com.sohayb.covidinfo.Retrofit.RetroFit
import kotlinx.android.synthetic.main.activity_all_countries.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NAME_SHADOWING")
class ListCountriesActivity : AppCompatActivity() {
val webService : RetroFit = RetroFit()
    var list : ArrayList<Country> = ArrayList()
    // Make a CoroutineContext variable
    //val main: CoroutineContext by lazy { Dispatchers.Main }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_countries)
        //getAllCaractersAndFillRecycler()
        //val bunldde = intent.extras
        list=intent.getParcelableArrayListExtra<Country>("countriesList") as ArrayList<Country>

        initCharactersRecyclerViewWithAll(list)

        resfreshLayout.setOnRefreshListener {
            initCharactersRecyclerViewWithAll(list)
        }




    }

    fun getAllCaractersAndFillRecycler() { // normally called from main at first
        CoroutineScope(Dispatchers.Main).launch { // or   GlobalScope.launch(Main)
            withContext(Dispatchers.IO) {
                webService.getApiInfo(this@ListCountriesActivity).GetAllCountries().await()
            }.also {
                initCharactersRecyclerViewWithAll(it)
            }

        }
    }

    private fun initCharactersRecyclerViewWithAll(countries: ArrayList<Country>) {

        resfreshLayout.isRefreshing=true
        val topSpacingDecorator = TopSpacingItemDecoration(16)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListCountriesActivity)
            if (itemDecorationCount == 0) {

                addItemDecoration(topSpacingDecorator)
            }

            adapter = Recycler(
                countries
            ).also {
                it.notifyDataSetChanged()
            }
        }

        resfreshLayout.isRefreshing=false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as? SearchView

        searchView?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
        }

        searchView!!.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(charSeq: String): Boolean {
                Log.i("t", "onQueryTextSubmit")
                val regex = Regex("[a-zA-Z]+")
                if (charSeq.isEmpty()) {
                    //initCharactersRecyclerViewWithAll()
                    getAllCaractersAndFillRecycler()
                    Toast.makeText(
                        this@ListCountriesActivity,
                        "Enter des caracteres valides",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                } else if (charSeq.contains(regex)) {
                    CoroutineScope(Dispatchers.Main).launch { // or   GlobalScope.launch(Main)
                        withContext(Dispatchers.IO) {
                            webService.getApiInfo(this@ListCountriesActivity).GetAllCountries().await()
                        }.also {
                            val newArray: ArrayList<Country> = ArrayList()
                            for (countri in it) {
                                if ((countri.country).toLowerCase(Locale.ROOT).contains(charSeq)) {
                                    newArray.add(countri)
                                }
                            }
                            initCharactersRecyclerViewWithAll(newArray)
                        }
                    }

                    return true
                    // var caracters : CaractersResponse  = getAllCaracters()
                } else {
                    Toast.makeText(
                        this@ListCountriesActivity,
                        "GOT SELECTED CARACTERS",
                        Toast.LENGTH_SHORT
                    ).show()
                    getAllCaractersAndFillRecycler()
                    //getCharacters(ids)
                    return true
                }

                //return true
            }

            override fun onQueryTextChange(charSeq: String): Boolean {
                Log.i("t", "onQueryTextSubmit")
                val regex = Regex("[a-zA-Z]+")
                if (charSeq.isEmpty()) {
                    getAllCaractersAndFillRecycler()
                    Toast.makeText(
                        this@ListCountriesActivity,
                        "Enter valid caracters",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                } else if (charSeq.contains(regex)) {
                    CoroutineScope(Dispatchers.Main).launch { // or   GlobalScope.launch(Main)
                        withContext(Dispatchers.IO) {
                            webService.getApiInfo(this@ListCountriesActivity).GetAllCountries().await()
                        }.also {
                            val newArray: ArrayList<Country> = ArrayList()
                            for (character in it) {
                                if ((character.country).toLowerCase(Locale.ROOT).contains(charSeq)) {
                                    newArray.add(character)
                                }
                            }
                            initCharactersRecyclerViewWithAll(newArray)
                        }
                    }
                    return true
                } else {
                    getAllCaractersAndFillRecycler()
                    return true
                }

            }
        })
        return true
    }
}