package com.tejas.relifemedicalsystemtest.ui.onboarding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseActivity
import com.tejas.relifemedicalsystemtest.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : BaseActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
    }
}