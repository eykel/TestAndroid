package com.android.test.testandroid.data.model

import com.google.gson.annotations.SerializedName


data class CheckInRequest(
    @SerializedName("name")
    val name : String,
    @SerializedName("email")
    val email: String,
    @SerializedName("eventId")
    val eventId: String
)