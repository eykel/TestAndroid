package com.android.test.testandroid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias EventList = ArrayList<Event>

@Parcelize
data class Event (
    val people: List<String>,
    val date: String,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: String,
    val title: String,
    val id: String
) : Parcelable