package io.viesure.bragiapp.data.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.viesure.bragiapp.data.NetworkConnectionManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class NetworkConnectionManagerTest {

    private lateinit var testScheduler : TestScheduler

    @Before
    fun before() {
        testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @After
    fun after() {
        RxJavaPlugins.setComputationSchedulerHandler(null)
    }

    @Test
    fun test_subscribe_to_network_update() {

        val networkConnectionManager = NetworkConnectionManager()
        val observable = networkConnectionManager.subscribeToNetworkUpdate()
        observable.subscribe{
            assert(ConnectionState.values().contains(it))
        }
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS)
    }

}