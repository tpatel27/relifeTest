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
import com.tejas.relifemedicalsystemtest.data.models.SignUpModel
import com.tejas.relifemedicalsystemtest.databinding.FragmentSignUpBinding
import com.tejas.relifemedicalsystemtest.ui.MainActivity
import com.tejas.relifemedicalsystemtest.ui.callbacks.OnDialogListeners
import com.tejas.relifemedicalsystemtest.ui.onboarding.FailedOnBoardState
import com.tejas.relifemedicalsystemtest.ui.onboarding.OnBoardViewModel
import com.tejas.relifemedicalsystemtest.ui.onboarding.SuccessfulOnBoardState
import com.tejas.relifemedicalsystemtest.utils.Constants
import com.tejas.relifemedicalsystemtest.utils.SharedPref
import com.tejas.relifemedicalsystemtest.utils.clearTaskAndOpenActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up),
    View.OnClickListener, OnDialogListeners {

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: OnBoardViewModel by viewModels()

    private lateinit var sharedPref: SharedPref

    private var phoneList = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(requireActivity())
        if (!sharedPref.getPhoneNumbers(Constants.SHARED_PREF_PHONE).isNullOrEmpty()) {
            phoneList = sharedPref.getPhoneNumbers(Constants.SHARED_PREF_PHONE)
        }
        binding.btnSignUp.setOnClickListener(this)
        binding.tvSignUpLogin.setOnClickListener(this)
        observeData()
    }

    private fun observeData() {
        viewModel.signUpState.observe(viewLifecycleOwner, Observer { state ->
            val signUpState = state ?: return@Observer
            when (signUpState) {
                is SuccessfulOnBoardState -> {
                    if (signUpState.isDataValid) {
                        lifecycleScope.launch {
                            showOtpDialog()
                        }
                    } else {
                        showErrorSnack(getString(R.string.error_unexpected))
                    }
                }
                is FailedOnBoardState -> {
                    showErrorSnack(signUpState.errorMessage)
                }
            }
        })
    }

    private fun showOtpDialog() {
        val otpDialog = OtpDialogFragment(listener = this@SignUpFragment)
        otpDialog.show(childFragmentManager, otpDialog.tag)
    }

    private fun saveDataToLocal() {
        var index = 0
        if (phoneList.size > 0) {
            index = phoneList.size
        }
        phoneList.add(index, binding.tiSignUpPhone.text?.trim().toString())
        sharedPref.savePhoneNumber(phoneList, Constants.SHARED_PREF_PHONE)
    }

    override fun otpSuccessListener(message: String) {
        showDefaultSnack(message = message)
        sessionManager.isLoggedIn = true
        saveDataToLocal()
        lifecycleScope.launch {
            delay(2000)
            context?.clearTaskAndOpenActivity(MainActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_up -> {
                viewModel.observeSignUpDataStates(
                    model = SignUpModel(
                        firstName = binding.tiFirstName.text?.trim().toString(),
                        lastName = binding.tiLastName.text?.trim().toString(),
                        phoneNumber = binding.tiSignUpPhone.text?.trim().toString(),
                        email = binding.tiEmail.text?.trim().toString(),
                    ),
                    context = requireActivity()
                )
            }
            R.id.tv_sign_up_login -> findNavController().navigateUp()
        }
    }
}