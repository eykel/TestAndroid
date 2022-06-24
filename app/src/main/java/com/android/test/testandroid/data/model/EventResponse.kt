package com.android.test.testandroid.data.model

import com.google.gson.annotations.SerializedName

typealias EventListResponse = ArrayList<EventResponse>

data class EventResponse (
    @SerializedName("people")
    val people: List<String>,
    @SerializedName("date")
    val date: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    val id: String
)
