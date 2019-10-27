package com.gamorales.testapplication.fixtures.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gamorales.testapplication.R
import com.gamorales.testapplication.core.api.RetrofitClient
import com.gamorales.testapplication.core.api.Services
import com.gamorales.testapplication.fixtures.controllers.RecyclerAdapter
import com.gamorales.testapplication.fixtures.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixturesFragment : Fragment() {

    lateinit var mFixturesList : RecyclerView
    lateinit var mContext: Context
    lateinit var mAdapter: RecyclerAdapter

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

    /**
     * Load the views from layout and fill the RecyclerView
     */
    fun setupUI(view: View) {
        val dateYear = view.findViewById(R.id.tvFixturesMonth) as TextView
        dateYear.text = "Some date"

        // Fill RecyclerView with data
        mAdapter = RecyclerAdapter(getFixtures(), mContext)
        mFixturesList = view.findViewById(R.id.rvFixturesList) as RecyclerView
    }

    /**
     * Will load fixtures from endpoint/microservice
     */
    fun getFixtures(): List<Fixture>{

        var fixtures: List<Fixture> = ArrayList()
        val service = RetrofitClient.retrofitInstance?.create(Services::class.java)
        val call: Call<List<Fixture>>? = service?.getFixtures()
        call?.enqueue(object: Callback<List<Fixture>>{
            override fun onFailure(call: Call<List<Fixture>>, t: Throwable) {
                Log.e("ERROR", t.message)
            }

            override fun onResponse(call: Call<List<Fixture>>, response: Response<List<Fixture>>) {
                mFixturesList.apply {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = RecyclerAdapter(response?.body()!!, mContext)
                }
            }
        })

        return fixtures
    }
}
