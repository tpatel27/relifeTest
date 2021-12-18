package com.tejas.relifemedicalsystemtest.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseActivity
import com.tejas.relifemedicalsystemtest.databinding.ActivitySplashBinding
import com.tejas.relifemedicalsystemtest.ui.onboarding.OnBoardingActivity
import com.tejas.relifemedicalsystemtest.utils.clearTaskAndOpenActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        moveToNext(hasLoggedIn = false)
    }

    private fun moveToNext(hasLoggedIn: Boolean) {
        lifecycleScope.launch {
            delay(1500)
            clearTaskAndOpenActivity(
                if (hasLoggedIn)
                    MainActivity::class.java else OnBoardingActivity::class.java
            )
        }
    }
}