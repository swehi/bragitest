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
import io.viesure.bragiapp.viewmodel.LoginViewModel

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
        return binding.root
    }

    private fun registerClick(){
        binding.forgotPasswordNavigateButton.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun observeEvents() {
        forgotPasswordViewModel.networkStatus.observe(viewLifecycleOwner) { state ->
            state?.let {
                showNetworkPopup(it)
            }
        }
    }
}