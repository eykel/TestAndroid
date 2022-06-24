package com.android.test.testandroid.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.test.testandroid.R
import com.android.test.testandroid.data.model.CheckInRequest
import com.android.test.testandroid.data.repository.EventRepository
import com.android.test.testandroid.onFailure
import com.android.test.testandroid.onSuccess
import com.android.test.testandroid.ui.getString
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val eventRepository: EventRepository
): ViewModel() {

    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData : LiveData<Boolean> = _progressLiveData

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private val _checkInDone = MutableLiveData<Unit>()
    val checkInDone : LiveData<Unit> = _checkInDone

    fun doCheckIn(name: String, email: String, eventId: String){
        viewModelScope.launch {
            _progressLiveData.value = true
            eventRepository.doCheckIn(
                CheckInRequest(
                    name = name,
                    email = email,
                    eventId = eventId
                )
            )
                .onSuccess(::handleDoCheckInSuccess)
                .onFailure (::handleDoCheckInError)
        }
    }

    private fun handleDoCheckInSuccess(response: Unit){
        _progressLiveData.value = false
        _checkInDone.value = response
    }
    private fun handleDoCheckInError(throwable: Throwable){
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