package com.tejas.relifemedicalsystemtest.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseActivity
import com.tejas.relifemedicalsystemtest.data.SessionManager
import com.tejas.relifemedicalsystemtest.databinding.ActivitySplashBinding
import com.tejas.relifemedicalsystemtest.ui.onboarding.OnBoardingActivity
import com.tejas.relifemedicalsystemtest.utils.clearTaskAndOpenActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        moveToNext()
    }

    private fun moveToNext() {
        lifecycleScope.launch {
            delay(1500)
            clearTaskAndOpenActivity(
                if (sessionManager.isLoggedIn)
                    MainActivity::class.java else OnBoardingActivity::class.java
            )
        }
    }
}