package com.tejas.relifemedicalsystemtest.ui.articles

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.databinding.FragmentSingleArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.R)
@AndroidEntryPoint
class SingleArticleFragment :
    BaseFragment<FragmentSingleArticleBinding>(R.layout.fragment_single_article) {

    private val singleArticleArgs by navArgs<SingleArticleFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.article = singleArticleArgs.articleData

        binding.tvSingleArticleSource.setOnClickListener {
            launchSource(singleArticleArgs.articleData.url)
        }

        binding.ivBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun launchSource(url: String) {
        if (url.isNotEmpty()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }
}