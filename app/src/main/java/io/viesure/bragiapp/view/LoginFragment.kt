package io.viesure.bragiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.viesure.bragiapp.databinding.LoginFragmentBinding
import io.viesure.bragiapp.navigation.navigateToSignup
import io.viesure.bragiapp.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: LoginFragmentBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        registerClick()
        observeEvents()
        return binding.root
    }

    private fun registerClick() {
        binding.loginNavigateButton.setOnClickListener {
            navigateToSignup()
        }
        binding.loginCheckConnectionButton.setOnClickListener {
            loginViewModel.networkStatus.value?.let { status -> showMessageSentPopup(status) }
        }
        binding.loginSendCommandsButton.setOnClickListener {
            loginViewModel.sendCommands()
        }
    }

    private fun observeEvents() {
        loginViewModel.networkStatus.observe(viewLifecycleOwner) { state ->
            state?.let {
                showNetworkPopup(it)
            }
        }
    }

}