package com.tejas.relifemedicalsystemtest.ui.articles

import android.os.Bundle
import android.view.View
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.databinding.FragmentSingleArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleArticleFragment :
    BaseFragment<FragmentSingleArticleBinding>(R.layout.fragment_single_article) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}