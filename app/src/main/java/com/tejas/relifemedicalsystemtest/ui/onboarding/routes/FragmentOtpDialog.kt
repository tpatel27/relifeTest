package com.tejas.relifemedicalsystemtest.ui.onboarding.routes

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.databinding.FragmentOtpDialogBinding
import com.tejas.relifemedicalsystemtest.ui.callbacks.OnDialogListeners
import com.tejas.relifemedicalsystemtest.ui.onboarding.FailedOnBoardState
import com.tejas.relifemedicalsystemtest.ui.onboarding.OnBoardViewModel
import com.tejas.relifemedicalsystemtest.ui.onboarding.SuccessfulOnBoardState
import kotlinx.coroutines.launch

class FragmentOtpDialog(
    private val listener: OnDialogListeners
) : DialogFragment() {

    private lateinit var dialogBinding: FragmentOtpDialogBinding
    private val viewModel: OnBoardViewModel by viewModels()
    private var otpCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialogBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_otp_dialog,
            null,
            false
        )

        dialogBinding.tvOtp.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                otpCode = ""
            }
            override fun onOTPComplete(otp: String) {
                otpCode = otp
            }
        }

        dialogBinding.tvOtpHint.setOnClickListener {
            Toast.makeText(requireActivity(), "OTP is 123456", Toast.LENGTH_SHORT).show()
        }

        dialogBinding.tvOtpSubmit.setOnClickListener {
            viewModel.observeOtpStates(otp = otpCode)
        }

        dialogBinding.tvOtpCancel.setOnClickListener {
            dismiss()
        }

        observeOtp()
        return dialogBinding.root
    }

    private fun observeOtp() {
        viewModel.otpState.observe(viewLifecycleOwner, Observer {  state ->
            val otpCodeState = state ?: return@Observer
            when (otpCodeState) {
                is SuccessfulOnBoardState -> {
                    if (otpCodeState.isDataValid) {
                        lifecycleScope.launch {
                            listener.otpSuccessListener(getString(R.string.label_otp_success))
                            dismiss()
                        }
                    } else {
                        showErrorSnack(getString(R.string.error_unexpected))
                    }
                }
                is FailedOnBoardState -> {
                    showErrorSnack(otpCodeState.errorMessage)
                }
            }
        })
    }

    private fun showErrorSnack(message: String?) {
        val snackBar = message?.let {
            Snackbar.make(dialogBinding.root, it, Snackbar.LENGTH_LONG)
        }
        snackBar?.setAction(android.R.string.ok) {
            snackBar.dismiss()
        }
        snackBar?.setBackgroundTint(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorRed
            )
        )
        snackBar?.show()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}