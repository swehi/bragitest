package io.viesure.bragiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.viesure.bragiapp.data.model.ConnectionState
import io.viesure.bragiapp.data.model.INetworkAware
import io.viesure.bragiapp.data.NetworkConnectionManager
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val networkConnectionManager: NetworkConnectionManager) : ViewModel(),
    INetworkAware {

    private val _networkStatus = MutableLiveData<ConnectionState?>()
    val networkStatus: LiveData<ConnectionState?> = _networkStatus
    private lateinit var networkDisposable: Disposable

    override fun subscribeToNetworkEvents() {
        networkDisposable = networkConnectionManager.subscribeToNetworkUpdate().subscribe {
            _networkStatus.postValue(it)
        }
    }

    override fun unSubscribeFromNetworkEvents() {
        networkDisposable.dispose()
    }
}