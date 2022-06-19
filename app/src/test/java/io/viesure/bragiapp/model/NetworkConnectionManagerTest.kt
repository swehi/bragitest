package io.viesure.bragiapp.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NetworkConnectionManagerTest {

    @Test
    fun testConsumer() {
        assert(true)
        val networkConnectionManager = NetworkConnectionManager()
        networkConnectionManager.addObserver {
            assert(ConnectionState.values().contains(it))
        }
    }

}