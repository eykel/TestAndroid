package com.android.test.testandroid.ui.view_model

import androidx.lifecycle.*
import com.android.test.testandroid.R
import com.android.test.testandroid.data.repository.EventRepository
import com.android.test.testandroid.domain.model.EventList
import com.android.test.testandroid.onFailure
import com.android.test.testandroid.onSuccess
import com.android.test.testandroid.ui.getString
import kotlinx.coroutines.launch

class EventListViewModel(
    private val eventRepository: EventRepository
    ): ViewModel(), DefaultLifecycleObserver {


    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData : LiveData<Boolean> = _progressLiveData

    private val _eventList = MutableLiveData<EventList>()
    val eventList : LiveData<EventList> = _eventList

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getEvents()
    }

    fun getEvents(){
        viewModelScope.launch {
            _progressLiveData.value = true
            eventRepository.getEvents()
                .onSuccess(::handleGetEventsSuccess)
                .onFailure(::handleGetEventsFailure)
        }
    }

    private fun handleGetEventsSuccess(eventList: EventList){
        _progressLiveData.value = false
        _eventList.value = eventList
    }

    private fun handleGetEventsFailure(throwable: Throwable){
        _progressLiveData.value = false
        /**
        * In real cases, I should ask the backEnd to return the correct message to user
        *excluding the front of responsibility of make treatment of error codes and things like that
        *in that case, the case above should change to throwable.message ?: getString(R.string.generic_error)
        * but for this test, to make it beautiful, I'll let a friendly message
        * */

        _error.value = getString(R.string.generic_error_text)
    }
}