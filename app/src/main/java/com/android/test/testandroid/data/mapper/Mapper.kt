package com.android.test.testandroid.data.mapper

import com.android.test.testandroid.Constants
import com.android.test.testandroid.data.model.EventListResponse
import com.android.test.testandroid.domain.model.Event
import com.android.test.testandroid.domain.model.EventList
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun EventListResponse.toEventList() : EventList =
     EventList(
         this.map {
             Event(
                 people = it.people,
                 date = getDateTime(it.date),
                 description = it.description,
                 image = it.image,
                 longitude = it.longitude,
                 latitude = it.latitude,
                 price= it.price.toCurrencyString(),
                 title = it.title,
                 id = it.id
             )
         }
     )

fun getDateTime(s: Long): String {
    return try {
        /**
         *Your timestamp is wrong...I don't know if its proposal..anyway
         * because of that, instead of use "dd/MM/yyyy pattern
         * I'll use just dd/MM, because the day and month is right
         *
         */
        val sdf = SimpleDateFormat(Constants.BRAZILIAN_DATE_PATTERN)
        val netDate = Date(s * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        s.toString()
    }
}

fun Double.toCurrencyString(
    locale: Locale = Locale(Constants.BRAZILIAN_LANGUAGE, Constants.BRAZILIAN_COUNTRY_INITIALS),
    decimalDigits: Int = 2
): String = NumberFormat.getCurrencyInstance(locale).apply {
    minimumFractionDigits = decimalDigits
    maximumFractionDigits = decimalDigits
    roundingMode = RoundingMode.HALF_EVEN
}.format(this)