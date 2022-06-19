package io.viesure.bragiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.viesure.bragiapp.model.ConnectionState
import io.viesure.bragiapp.model.NetworkConnectionManager
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(networkConnectionManager: NetworkConnectionManager) :
    ViewModel() {

    private val _networkStatus = MutableLiveData<ConnectionState?>()
    val networkStatus: LiveData<ConnectionState?> = _networkStatus

    private val consumer = Consumer<ConnectionState> {
        _networkStatus.postValue(it)
    }

    init {
        networkConnectionManager.addObserver(consumer)
    }

    fun sendCommands() {
        val commands = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        Observable.fromIterable(commands).subscribeOn(Schedulers.single()).subscribe {
            Thread.sleep(it * 1000L)
            println("Command ID: $it")
        }
    }
}