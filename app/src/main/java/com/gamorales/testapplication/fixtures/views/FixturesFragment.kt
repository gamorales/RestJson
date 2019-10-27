package com.gamorales.testapplication.fixtures.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gamorales.testapplication.R
import com.gamorales.testapplication.results.controllers.RecyclerAdapter
import com.gamorales.testapplication.results.models.Fixture

class FixturesFragment : Fragment() {

    lateinit var mFixturesList : RecyclerView
    lateinit var mContext: Context

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
        return inflater.inflate(R.layout.fixtures_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    fun setupUI(view: View) {
        val dateYear = view.findViewById(R.id.tvFixturesMonth) as TextView
        dateYear.text = "Some date"

        val mAdapter = RecyclerAdapter(getFixtures(), mContext)
        mFixturesList = view.findViewById(R.id.rvFixturesList) as RecyclerView
        mFixturesList.setHasFixedSize(true)
        mFixturesList.layoutManager = LinearLayoutManager(mContext)
        mFixturesList.adapter = mAdapter
    }

    /**
     * Will load fixtures from endpoint/microservice
     */
    fun getFixtures(): MutableList<Fixture>{
        var fixtures:MutableList<Fixture> = ArrayList()
        fixtures.add(
            Fixture(
                987847,
                "FixtureUpcoming",
                "Manchester City",
                "Chelsea",
                "2019-02-10T16:00:00.000Z",
                "Premier League",
                "Etihad Stadium",
                "preMatch"
            )
        )
        fixtures.add(
            Fixture(
                1036495,
                "FixtureUpcoming",
                "Malmö FF",
                "Chelsea",
                "2019-02-14T20:00:00.000Z",
                "UEFA Europa League",
                "Swedbank Stadion",
                "preMatch"
            )
        )
        fixtures.add(
            Fixture(
                987856,
                "FixtureUpcoming",
                "Chelsea",
                "Brighton",
                "2019-02-24T12:00:00.000Z",
                "Premier League",
                "Stamford Bridge",
                "postponed"
            )
        )
        fixtures.add(
            Fixture(
                1045272,
                "FixtureUpcoming",
                "Chelsea",
                "Manchester City",
                "2019-02-24T16:30:00.000Z",
                "Carabao Cup",
                "Wembley Stadium",
                "preMatch"
            )
        )
        return fixtures
    }
}