package com.tejas.relifemedicalsystemtest.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.tejas.relifemedicalsystemtest.R

abstract class BaseFragment<T : ViewDataBinding> constructor(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    open fun showDefaultSnack(message: String?) {
        val snackBar = message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
        }
        snackBar?.setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.colorPrimary))
        snackBar?.show()
    }

    open fun showErrorSnack(message: String?) {
        val snackBar = message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
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
}