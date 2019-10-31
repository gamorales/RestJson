package com.gamorales.testapplication.fixtures.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gamorales.testapplication.R
import com.gamorales.testapplication.core.api.RetrofitClient
import com.gamorales.testapplication.core.api.Services
import com.gamorales.testapplication.core.controllers.RecyclerAdapter
import com.gamorales.testapplication.core.models.*
import kotlinx.android.synthetic.main.data_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FixturesFragment : Fragment() {

    lateinit var mFixturesList : RecyclerView
    lateinit var mContext: Context
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
        mContext = activity!!.applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.data_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    /**
     * Load the views from layout and fill the RecyclerView
     */
    fun setupUI(view: View) {
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar
        val dateYear = view.findViewById(R.id.tvMonth) as TextView
        val months = view.findViewById(R.id.spMonths) as Spinner
        // Create an ArrayAdapter using a simple spinner layout and languages array

        spMonths?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dateYear.text = parent?.getItemAtPosition(position).toString()
            }

        }

        // Fill RecyclerView with data
        getFixtures()
        mFixturesList = view.findViewById(R.id.rvList) as RecyclerView
    }

    /**
     * Will load fixtures from endpoint/microservice
     */
    fun getFixtures(){

        var fixtureHM : HashMap<String, List<Fixture>> = HashMap()
        var fixtures: MutableList<Fixture> = ArrayList()
        val service = RetrofitClient.retrofitInstance?.create(Services::class.java)
        val call: Call<List<Fixture>>? = service?.getFixtures()
        call?.enqueue(object: Callback<List<Fixture>>{
            override fun onFailure(call: Call<List<Fixture>>, t: Throwable) {
                Log.e("ERROR", t.message)
            }

            override fun onResponse(call: Call<List<Fixture>>, response: Response<List<Fixture>>) {
                val formatter = DateTimeFormatter.ISO_DATE_TIME
                val months = mutableListOf<String>()
                for (fixture in response?.body()!!) {
                    val zonedDateTime = ZonedDateTime.parse(fixture.date.toString(), formatter)
                    val monthYear: String = zonedDateTime.month.toString() + " " + zonedDateTime.year
                    months.add(monthYear)

                    fixtures.add(
                        Fixture(
                            fixture.id,
                            fixture.type,
                            Team(
                                fixture.homeTeam?.id,
                                fixture.homeTeam?.name,
                                fixture.homeTeam?.shortName,
                                fixture.homeTeam?.abbr,
                                fixture.homeTeam?.alias
                            ),
                            Team(
                                fixture.awayTeam?.id,
                                fixture.awayTeam?.name,
                                fixture.awayTeam?.shortName,
                                fixture.awayTeam?.abbr,
                                fixture.awayTeam?.alias
                            ),
                            fixture.date,
                            CompetitionStage(
                                Competition(
                                    fixture.competitionStage?.competition?.id,
                                    fixture.competitionStage?.competition?.name
                                ),
                                fixture.competitionStage?.stage,
                                fixture.competitionStage?.leg
                            ),
                            Venue(
                                fixture.venue?.id,
                                fixture.venue?.name
                            ),
                            fixture.state,
                            null,
                            null,
                            null
                        )
                    )

                    fixtureHM.put(monthYear, fixtures)
                    Log.i("INFO", "ALGO")
                }

                //Kotlin language to get the same results
                for(key in fixtureHM.keys){
                    Log.i("some", "Element at key $key : ${fixtureHM[key]}")
                }

                val strMonths = months.distinct()
                val aa = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, strMonths)
                // Set layout to use when the list of choices appear
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Set Adapter to Spinner
                spMonths!!.setAdapter(aa)

                Log.i("INFO", "ALGO")
                mFixturesList.apply {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = RecyclerAdapter(response?.body()!!, mContext)
                }
                progressBar.visibility = View.INVISIBLE
            }
        })
    }
}
