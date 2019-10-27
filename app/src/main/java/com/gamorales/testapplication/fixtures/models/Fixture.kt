package com.gamorales.testapplication.fixtures.models

import com.google.gson.annotations.SerializedName

data class Fixture (
    @SerializedName("id") var id:Int?,
    @SerializedName("type") var type:String?,
    @SerializedName("homeTeam") var homeTeam:Team?,
    @SerializedName("awayTeam") var awayTeam:Team?,
    @SerializedName("date") var date:String?,
    @SerializedName("competitionStage") var competitionStage: CompetitionStage?,
    @SerializedName("venue") var venue:Venue?,
    @SerializedName("state") var state:String?
)
