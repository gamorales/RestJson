package com.gamorales.testapplication.core.models

import com.google.gson.annotations.SerializedName

data class Score (
    @SerializedName("home") var home: Int,
    @SerializedName("away") var away: Int
)