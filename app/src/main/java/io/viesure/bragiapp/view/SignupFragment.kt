package io.viesure.bragiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.viesure.bragiapp.databinding.SignupFragmentBinding
import io.viesure.bragiapp.navigation.navigateToForgotPassword
import io.viesure.bragiapp.viewmodel.SignupViewModel

@AndroidEntryPoint
class SignupFragment : BaseFragment() {

    lateinit var binding: SignupFragmentBinding
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupFragmentBinding.inflate(inflater, container, false)
        registerClick()
        observeEvents()
        return binding.root
    }

    private fun registerClick() {
        binding.signupNavigateButton.setOnClickListener {
            navigateToForgotPassword()
        }
        binding.signupCheckConnectionButton.setOnClickListener {
            signupViewModel.networkStatus.value?.let { status -> showMessageSentPopup(status) }
        }
    }

    private fun observeEvents() {
        signupViewModel.networkStatus.observe(viewLifecycleOwner) { state ->
            state?.let {
                showNetworkPopup(it)
            }
        }
    }
}