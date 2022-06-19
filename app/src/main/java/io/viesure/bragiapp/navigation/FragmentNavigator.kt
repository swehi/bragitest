package io.viesure.bragiapp.navigation

import androidx.navigation.fragment.findNavController
import io.viesure.bragiapp.R
import io.viesure.bragiapp.view.ForgotPasswordFragment
import io.viesure.bragiapp.view.LoginFragment
import io.viesure.bragiapp.view.SignupFragment

fun ForgotPasswordFragment.navigateToLogin() {
    findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
}

fun LoginFragment.navigateToSignup() {
    findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
}

fun SignupFragment.navigateToForgotPassword() {
    findNavController().navigate(R.id.action_signupFragment_to_forgotPasswordFragment)
}