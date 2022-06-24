package com.android.test.testandroid.data.repository

import com.android.test.testandroid.Result
import com.android.test.testandroid.data.mapper.toEventList
import com.android.test.testandroid.data.model.CheckInRequest
import com.android.test.testandroid.data.networking.EventNetworking
import com.android.test.testandroid.domain.model.EventList
import com.android.test.testandroid.mapNotNull
import com.android.test.testandroid.notNull
import com.android.test.testandroid.result

class EventRepository(private val eventNetworking: EventNetworking) {

    suspend fun getEvents() : Result<EventList> =
        result { eventNetworking.getEvents() }.mapNotNull { it.toEventList() }

    suspend fun doCheckIn(checkIn : CheckInRequest) : Result<Unit> =
        result { eventNetworking.doCheckIn(checkIn) }.notNull()
}