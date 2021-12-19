package com.tejas.relifemedicalsystemtest.ui.onboarding.routes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.data.SessionManager
import com.tejas.relifemedicalsystemtest.databinding.FragmentLoginBinding
import com.tejas.relifemedicalsystemtest.ui.MainActivity
import com.tejas.relifemedicalsystemtest.ui.callbacks.OnDialogListeners
import com.tejas.relifemedicalsystemtest.ui.onboarding.FailedOnBoardState
import com.tejas.relifemedicalsystemtest.ui.onboarding.OnBoardViewModel
import com.tejas.relifemedicalsystemtest.ui.onboarding.SuccessfulOnBoardState
import com.tejas.relifemedicalsystemtest.utils.clearTaskAndOpenActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login),
    View.OnClickListener, OnDialogListeners {

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: OnBoardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener(this)
        binding.tvLoginSignUp.setOnClickListener(this)
        observeData()
    }

    private fun observeData() {
        viewModel.loginPhoneState.observe(viewLifecycleOwner, Observer { state ->
            val loginState = state ?: return@Observer
            when (loginState) {
                is SuccessfulOnBoardState -> {
                    if (loginState.isDataValid) {
                        lifecycleScope.launch {
                            showOtpDialog()
                        }
                    } else {
                        showErrorSnack(getString(R.string.error_unexpected))
                    }
                }
                is FailedOnBoardState -> {
                    showErrorSnack(loginState.errorMessage)
                }
            }
        })
    }

    private fun showOtpDialog() {
        val otpDialog = OtpDialogFragment(listener = this@LoginFragment)
        otpDialog.show(childFragmentManager, otpDialog.tag)
    }

    override fun otpSuccessListener(message: String) {
        showDefaultSnack(message = message)
        sessionManager.isLoggedIn = true
        lifecycleScope.launch {
            delay(2000)
            context?.clearTaskAndOpenActivity(MainActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                viewModel.observePhoneStates(
                    phone = binding.tiPhone.text?.trim().toString()
                )
            }
            R.id.tv_login_sign_up -> findNavController().navigate(LoginFragmentDirections.moveToSignUpAction())
        }
    }
}