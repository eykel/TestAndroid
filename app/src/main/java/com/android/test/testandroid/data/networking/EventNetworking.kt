package com.android.test.testandroid.data.networking

import com.android.test.testandroid.data.model.CheckInRequest
import com.android.test.testandroid.data.service.EventService

class EventNetworking(private val eventService: EventService) {


    suspend fun getEvents() = eventService.getEvents()

    suspend fun doCheckIn(checkInRequest: CheckInRequest) = eventService.doCheckIn(checkInRequest)
}