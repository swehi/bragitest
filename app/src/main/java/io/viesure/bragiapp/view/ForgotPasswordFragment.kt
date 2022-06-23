package io.viesure.bragiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.viesure.bragiapp.databinding.ForgotPasswordFragmentBinding
import io.viesure.bragiapp.navigation.navigateToLogin
import io.viesure.bragiapp.viewmodel.ForgotPasswordViewModel

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment() {

    lateinit var binding: ForgotPasswordFragmentBinding
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForgotPasswordFragmentBinding.inflate(inflater, container, false)
        registerClick()
        observeEvents()
        return binding.root
    }

    private fun registerClick() {
        binding.forgotPasswordNavigateButton.setOnClickListener {
            navigateToLogin()
        }
        binding.forgotPasswordCheckConnectionButton.setOnClickListener {
            forgotPasswordViewModel.networkStatus.value?.let { status -> showMessageSentPopup(status) }
        }
    }

    private fun observeEvents() {
        forgotPasswordViewModel.networkStatus.observe(viewLifecycleOwner) { state ->
            state?.let {
                showNetworkPopup(it)
            }
        }
    }

    override fun onResume() {
        forgotPasswordViewModel.subscribeToNetworkEvents()
        super.onResume()
    }

    override fun onPause() {
        forgotPasswordViewModel.unSubscribeFromNetworkEvents()
        super.onPause()
    }
}