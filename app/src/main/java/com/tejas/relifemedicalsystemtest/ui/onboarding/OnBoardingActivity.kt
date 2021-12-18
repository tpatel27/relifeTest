package com.tejas.relifemedicalsystemtest.ui.onboarding

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseActivity
import com.tejas.relifemedicalsystemtest.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : BaseActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_auth) as NavHostFragment
//        val inflater = navHostFragment.navController.navInflater
//        val graph = inflater.inflate(R.navigation.nav_graph_auth)
//        graph.startDestination = R.id.loginFragment
//        val navController = navHostFragment.navController
//        navController.graph = graph
    }
}