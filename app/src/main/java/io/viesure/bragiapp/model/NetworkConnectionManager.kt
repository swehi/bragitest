package io.viesure.bragiapp.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton to dispatch network connectivity status through the app
 */
@Singleton
class NetworkConnectionManager @Inject constructor() {

    private val observable: Observable<ConnectionState> = Observable.interval(5, TimeUnit.SECONDS)
        .flatMap {
            return@flatMap Observable.create<ConnectionState> { emitter ->
                emitter.onNext(ConnectionState.values().toList().shuffled().first())
            }
        }.subscribeOn(Schedulers.computation()).share()

    fun addObserver(consumer: Consumer<ConnectionState>) {
        observable.subscribe(consumer)
    }

}
