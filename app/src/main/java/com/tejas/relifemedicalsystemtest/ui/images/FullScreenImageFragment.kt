package com.tejas.relifemedicalsystemtest.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.databinding.LayoutImageFullscreenBinding
import com.tejas.relifemedicalsystemtest.ui.callbacks.OnDialogListeners

class FullScreenImageFragment(
    private val listener: OnDialogListeners,
    private val imageUrl: String
) : DialogFragment() {

    private lateinit var dialogBinding: LayoutImageFullscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialogBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_image_fullscreen,
            null,
            false
        )

        Glide.with(dialogBinding.root)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(dialogBinding.ivFullScreenImage)

        dialogBinding.ivDownloadImage.setOnClickListener {
            dismiss()
            Toast.makeText(requireActivity(), "Downloading image...", Toast.LENGTH_SHORT).show()
            listener.onImageDownloadListener()
        }

        dialogBinding.ivCloseExpandedImage.setOnClickListener {
            dismiss()
        }

        return dialogBinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}