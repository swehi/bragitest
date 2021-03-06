package io.viesure.bragiapp.viewmodel

import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.viesure.bragiapp.data.CommandService
import io.viesure.bragiapp.data.model.ConnectionState
import io.viesure.bragiapp.data.NetworkConnectionManager
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import javax.inject.Inject


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    @Inject
    lateinit var commandService: CommandService

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun test_subscribe_to_network_update() {
        val mockNetworkConnectionManager: NetworkConnectionManager = mockk(relaxed = true)

        every { mockNetworkConnectionManager.subscribeToNetworkUpdate() } returns Observable.just(
            ConnectionState.CONNECTING
        )
        val viewModel = LoginViewModel(mockNetworkConnectionManager, commandService)
        viewModel.subscribeToNetworkEvents()
        shadowOf(Looper.getMainLooper()).idle()
        assert(viewModel.networkStatus.value?.equals(ConnectionState.CONNECTING)!!)
    }

    @Test
    fun test_send_commands() {
        val mockCommandService: CommandService = mockk(relaxed = true)

        every { mockCommandService.generateCommands() } returns Observable.just(2)
        val viewModel = LoginViewModel(networkConnectionManager, mockCommandService)
        viewModel.sendCommands()
        verify { mockCommandService.generateCommands() }
    }

}