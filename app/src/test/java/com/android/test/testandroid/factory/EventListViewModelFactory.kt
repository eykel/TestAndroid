package com.android.test.testandroid.factory

import com.android.test.testandroid.domain.model.Event
import com.android.test.testandroid.domain.model.EventList

object EventListViewModelFactory {

    fun createGetEventsResult() =
        EventList(
            listOf(
                Event(
                    people = listOf(),
                    date = "20/05",
                    description = "Descrição",
                    image = "image",
                    longitude = 234234.0,
                    latitude = 234234.0,
                    price= "R$44",
                    title = "title",
                    id = "1"
                ),
                Event(
                    people = listOf(),
                    date = "20/05",
                    description = "Descrição",
                    image = "image",
                    longitude = 234234.0,
                    latitude = 234234.0,
                    price= "R$44",
                    title = "title",
                    id = "2"
                )
            )
        )
}