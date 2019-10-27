package com.gamorales.testapplication.core.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://storage.googleapis.com/cdn-og-test-api/test-task/"

    // create a retrofit instance, only if it has not been created yet.
    val retrofitInstance: Retrofit?
    get() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }
}