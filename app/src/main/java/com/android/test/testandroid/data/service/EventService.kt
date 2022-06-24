package com.android.test.testandroid.data.service

import com.android.test.testandroid.data.model.CheckInRequest
import com.android.test.testandroid.data.model.EventListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventService {

    @GET("events")
    suspend fun getEvents() : EventListResponse

    @POST("checkin")
    suspend fun doCheckIn(@Body checkInRequest: CheckInRequest)
}