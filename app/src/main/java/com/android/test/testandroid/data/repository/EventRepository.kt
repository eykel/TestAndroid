package com.android.test.testandroid.data.repository

import com.android.test.testandroid.data.helper.mapNotNull
import com.android.test.testandroid.data.helper.notNull
import com.android.test.testandroid.data.helper.result
import com.android.test.testandroid.data.mapper.toEventList
import com.android.test.testandroid.data.model.CheckInRequest
import com.android.test.testandroid.data.model.EventList
import com.android.test.testandroid.data.networking.EventNetworking
import com.android.test.testandroid.data.helper.Result

class EventRepository(private val eventNetworking: EventNetworking) {

    suspend fun getEvents() : Result<EventList> =
        result { eventNetworking.getEvents() }.mapNotNull { it.toEventList() }

    suspend fun doCheckIn(checkIn : CheckInRequest) : Result<Unit> =
        result { eventNetworking.doCheckIn(checkIn) }.notNull()
}