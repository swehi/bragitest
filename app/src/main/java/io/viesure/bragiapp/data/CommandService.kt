package io.viesure.bragiapp.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CommandService @Inject constructor() {

    fun generateCommands(): Observable<Int> {
        val commands = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        return Observable.fromIterable(commands).subscribeOn(Schedulers.single())
    }

}