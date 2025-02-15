package com.example.statesapp

import com.google.gson.annotations.SerializedName

data class StatesResponse(
    @SerializedName("data") val data: List<StateData>?
)

data class StateData(
    @SerializedName("State") val state: String?,
    @SerializedName("Population") val population: Int?
)