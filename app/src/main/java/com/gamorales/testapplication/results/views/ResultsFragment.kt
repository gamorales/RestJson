package com.gamorales.testapplication.results.views

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
import com.gamorales.testapplication.core.controllers.RecyclerAdapter
import com.gamorales.testapplication.core.models.Fixture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultsFragment : Fragment() {

    lateinit var mResultsList : RecyclerView
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
        val dateYear = view.findViewById(R.id.tvMonth) as TextView
        dateYear.text = "Some date"

        // Fill RecyclerView with data
        getResults()
        mResultsList = view.findViewById(R.id.rvList) as RecyclerView
    }

    /**
     * Will load results from endpoint/microservice
     */
    fun getResults(){

        var results: List<Fixture> = ArrayList()
        val service = RetrofitClient.retrofitInstance?.create(Services::class.java)
        val call: Call<List<Fixture>>? = service?.getResults()
        call?.enqueue(object: Callback<List<Fixture>> {
            override fun onFailure(call: Call<List<Fixture>>, t: Throwable) {
                Log.e("ERROR", t.message)
            }

            override fun onResponse(call: Call<List<Fixture>>, response: Response<List<Fixture>>) {
                mResultsList.apply {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = RecyclerAdapter(response?.body()!!, mContext)
                }
            }
        })
    }
}