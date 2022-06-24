package com.android.test.testandroid.view_model


import com.android.test.testandroid.Result
import com.android.test.testandroid.data.repository.EventRepository
import com.android.test.testandroid.factory.EventListViewModelFactory
import com.android.test.testandroid.ui.view_model.EventListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.test.testandroid.TestCoroutineRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EventListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val eventRepository = mockk<EventRepository>()
    private val eventsResult = EventListViewModelFactory.createGetEventsResult()
    private lateinit var eventListViewModel: EventListViewModel

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        eventListViewModel = EventListViewModel(eventRepository)
    }

    @Test
    fun `When getEvents is called and return success, progressLiveData and eventList should Change`() =
        testCoroutineRule.runBlockingTest {
            coEvery {
                eventRepository.getEvents()
            } returns Result.success(eventsResult)

            eventListViewModel.getEvents()

            assertEquals(eventListViewModel.progressLiveData.value, false)
            assertEquals(eventListViewModel.eventList.value,  eventsResult)
        }


}