package com.tejas.relifemedicalsystemtest.ui.onboarding

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tejas.relifemedicalsystemtest.data.models.SignUpModel
import com.tejas.relifemedicalsystemtest.utils.Constants
import com.tejas.relifemedicalsystemtest.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor() : ViewModel() {

    private lateinit var sharedPref: SharedPref

    private val _loginPhoneState = MutableLiveData<OnBoardStates>()
    val loginPhoneState: LiveData<OnBoardStates> = _loginPhoneState

    private val _signUpState = MutableLiveData<OnBoardStates>()
    val signUpState: LiveData<OnBoardStates> = _signUpState

    private val _otpState = MutableLiveData<OnBoardStates>()
    val otpState: LiveData<OnBoardStates> = _otpState

    fun observePhoneStates(phone: String, context: Context) {
        when {
            phone.isEmpty() -> _loginPhoneState.value = FailedOnBoardState(
                errorMessage = "Phone number cannot be empty."
            )

            !isPhoneNumberValid(phone) -> _loginPhoneState.value = FailedOnBoardState(
                errorMessage = "Invalid phone number."
            )

            !numberExists(context = context, number = phone) -> _loginPhoneState.value =
                FailedOnBoardState(
                    errorMessage = "Phone number not registered, please sign up"
                )

            else -> _loginPhoneState.value = SuccessfulOnBoardState(isDataValid = true)
        }
    }

    fun observeSignUpDataStates(model: SignUpModel, context: Context) {
        when {
            model.firstName.isEmpty() -> _signUpState.value = FailedOnBoardState(
                errorMessage = "First name cannot be empty."
            )

            model.lastName.isEmpty() -> _signUpState.value = FailedOnBoardState(
                errorMessage = "Last name cannot be empty."
            )

            model.phoneNumber.isEmpty() -> _signUpState.value = FailedOnBoardState(
                errorMessage = "Phone number cannot be empty."
            )

            !isPhoneNumberValid(model.phoneNumber) -> _signUpState.value = FailedOnBoardState(
                errorMessage = "Invalid phone number."
            )

            numberExists(context = context, number = model.phoneNumber) -> _signUpState.value =
                FailedOnBoardState(
                    errorMessage = "Phone number already registered, please login"
                )

            else -> _signUpState.value = SuccessfulOnBoardState(isDataValid = true)
        }
    }

    fun observeOtpStates(otp: String) {
        when {
            otp.isEmpty() -> _otpState.value = FailedOnBoardState(
                errorMessage = "OTP cannot be empty."
            )

            otp == "123456" -> _otpState.value = SuccessfulOnBoardState(isDataValid = true)

            else -> _otpState.value = FailedOnBoardState(
                errorMessage = "Entered OTP is incorrect"
            )
        }
    }

    private fun numberExists(context: Context, number: String): Boolean {
        sharedPref = SharedPref(context)
        return number in sharedPref.getPhoneNumbers(Constants.SHARED_PREF_PHONE)
    }

    private fun isPhoneNumberValid(number: String): Boolean {
        return number.length == 10
    }
}