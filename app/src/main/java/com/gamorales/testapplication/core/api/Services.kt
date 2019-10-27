package com.gamorales.testapplication.core.api

import com.gamorales.testapplication.core.models.Fixture
import retrofit2.Call
import retrofit2.http.GET

interface Services {

    @GET("fixtures.json")
    fun getFixtures() : Call<List<Fixture>>

    @GET("results.json")
    fun getResults() : Call<List<Fixture>>

}