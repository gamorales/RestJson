package com.gamorales.testapplication.results.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gamorales.testapplication.R
import com.gamorales.testapplication.results.models.Fixture

class ResultsFragment : Fragment() {

    private lateinit var homeViewModel: Fixture

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*homeViewModel =
            ViewModelProviders.of(this).get(Fixture::class.java)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }
}