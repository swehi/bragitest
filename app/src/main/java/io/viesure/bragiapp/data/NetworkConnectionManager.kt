package io.viesure.bragiapp.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.viesure.bragiapp.data.model.ConnectionState
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton to dispatch network connectivity status through the app
 */
@Singleton
class NetworkConnectionManager @Inject constructor() {

    private var previousState: ConnectionState? = null

    private val observable: Observable<ConnectionState> = Observable.interval(5, TimeUnit.SECONDS)
        .flatMap {
            return@flatMap getNetworkStatus()
        }.subscribeOn(Schedulers.computation()).share()

    fun subscribeToNetworkUpdate() : Observable<ConnectionState> {
        return observable
    }

    private fun getNetworkStatus(): Observable<ConnectionState> {
        return Observable.fromArray(ConnectionState.values())
            .map { states ->
                previousState?.let { state ->
                    states.toMutableList().filterNot { it == state }.shuffled().first()
                        .also { previousState = it }
                } ?: states.toMutableList().shuffled().first().also { previousState = it }
            }
            .share()
    }

}
