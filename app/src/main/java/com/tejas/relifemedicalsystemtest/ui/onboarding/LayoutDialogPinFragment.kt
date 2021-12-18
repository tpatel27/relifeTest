package com.tejas.relifemedicalsystemtest.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.databinding.LayoutDialogPinBinding

class LayoutDialogPinFragment(
//    private val onIssueSubmit: OnJiraReportSubmitListener
) : DialogFragment() {

    private lateinit var dialogBinding: LayoutDialogPinBinding
    private val viewModel: OnBoardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialogBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_dialog_pin,
            null,
            false
        )

        dialogBinding.tvOtpReSend.setOnClickListener {
            //resend code
        }

        dialogBinding.tvOtpSubmit.setOnClickListener {
            //submit
        }

        dialogBinding.tvOtpCancel.setOnClickListener {
            dismiss()
        }

        return dialogBinding.root
    }
}