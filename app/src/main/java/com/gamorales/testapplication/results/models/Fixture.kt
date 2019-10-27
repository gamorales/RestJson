package com.gamorales.testapplication.results.models

import com.google.gson.annotations.SerializedName

data class Fixture (
    @SerializedName("id") var id:Int,
    @SerializedName("type") var type:String,
    @SerializedName("homeTeam") var homeTeam:String,
    @SerializedName("awayTeam") var awayTeam:String,
    @SerializedName("date") var date:String,
    @SerializedName("competitionStage") var competitionStage: String,
    @SerializedName("venue") var venue:String,
    @SerializedName("state") var state:String

)

