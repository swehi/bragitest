package io.viesure.bragiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.viesure.bragiapp.model.ConnectionState
import io.viesure.bragiapp.model.NetworkConnectionManager
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(networkConnectionManager: NetworkConnectionManager) :
    ViewModel() {

    private val _networkStatus = MutableLiveData<ConnectionState?>()
    val networkStatus: LiveData<ConnectionState?> = _networkStatus

    init {
        networkConnectionManager.addObserver {
            _networkStatus.postValue(it)
        }
    }

}