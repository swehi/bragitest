package io.viesure.bragiapp.view

import android.os.Looper
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import io.viesure.bragiapp.R
import io.viesure.bragiapp.launchFragmentInHiltContainer
import io.viesure.bragiapp.data.CommandService
import io.viesure.bragiapp.data.model.ConnectionState
import io.viesure.bragiapp.data.NetworkConnectionManager
import io.viesure.bragiapp.viewmodel.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val networkConnectionManager: NetworkConnectionManager =
        mockk(relaxed = true)

    @BindValue
    @JvmField
    val commandService: CommandService = mockk(relaxed = true)

    @BindValue
    @JvmField
    val viewModel: LoginViewModel = spyk(LoginViewModel(networkConnectionManager, commandService))

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test_connection_estabilished() {
        every { networkConnectionManager.subscribeToNetworkUpdate() } returns Observable.just(
            ConnectionState.CONNECTION_ESTABILISHED
        )
        launchFragmentInHiltContainer<LoginFragment> {
            this as LoginFragment

            verify { viewModel.subscribeToNetworkEvents() }

            assert(binding.loginNavigateButton.isVisible)
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assert(viewModel.networkStatus.value!! == ConnectionState.CONNECTION_ESTABILISHED)


            Shadows.shadowOf(Looper.getMainLooper()).idle()
            val connectionToast = ShadowToast.getLatestToast()
            assert(connectionToast.view!!.findViewById<TextView>(R.id.popup_text).text == ConnectionState.CONNECTION_ESTABILISHED.name)

            binding.loginCheckConnectionButton.performClick()
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertEquals(ShadowToast.getTextOfLatestToast(), getString(R.string.message_sent))
        }
    }

    @Test
    fun test_connecting() {
        every { networkConnectionManager.subscribeToNetworkUpdate() } returns Observable.just(
            ConnectionState.CONNECTING
        )
        launchFragmentInHiltContainer<LoginFragment> {
            this as LoginFragment

            verify { viewModel.subscribeToNetworkEvents() }

            assert(binding.loginNavigateButton.isVisible)
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assert(viewModel.networkStatus.value!! == ConnectionState.CONNECTING)


            Shadows.shadowOf(Looper.getMainLooper()).idle()
            val connectionToast = ShadowToast.getLatestToast()
            assert(connectionToast.view!!.findViewById<TextView>(R.id.popup_text).text == ConnectionState.CONNECTING.name)

            binding.loginCheckConnectionButton.performClick()
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertEquals(ShadowToast.shownToastCount(), 1)
        }
    }

    @Test
    fun test_connection_error() {
        every { networkConnectionManager.subscribeToNetworkUpdate() } returns Observable.just(
            ConnectionState.CONNECTION_ERROR
        )
        launchFragmentInHiltContainer<LoginFragment> {
            this as LoginFragment

            verify { viewModel.subscribeToNetworkEvents() }

            assert(binding.loginNavigateButton.isVisible)
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assert(viewModel.networkStatus.value!! == ConnectionState.CONNECTION_ERROR)


            Shadows.shadowOf(Looper.getMainLooper()).idle()
            val connectionToast = ShadowToast.getLatestToast()
            assert(connectionToast.view!!.findViewById<TextView>(R.id.popup_text).text == ConnectionState.CONNECTION_ERROR.name)

            binding.loginCheckConnectionButton.performClick()
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            assertEquals(ShadowToast.shownToastCount(), 1)
        }
    }

    @Test
    fun test_send_commands() {
        launchFragmentInHiltContainer<LoginFragment> {
            this as LoginFragment

            verify { viewModel.subscribeToNetworkEvents() }

            assert(binding.loginSendCommandsButton.isVisible)
            binding.loginSendCommandsButton.performClick()

            verify { viewModel.sendCommands() }

        }
    }

    @Test
    fun test_navigation_to_signup(){
        launchFragmentInHiltContainer<LoginFragment> {
            this as LoginFragment
            val navController = mockk<NavController>(relaxed = true)
            Navigation.setViewNavController(requireView(), navController)


            assert(binding.loginNavigateButton.isVisible)
            binding.loginNavigateButton.performClick()
            verify { navController.navigate(R.id.action_loginFragment_to_signupFragment) }
        }
    }
}