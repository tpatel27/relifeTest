package com.tejas.relifemedicalsystemtest.ui.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.databinding.FragmentArticleListBinding
import com.tejas.relifemedicalsystemtest.network.SingleArticleResponse
import dagger.hilt.android.AndroidEntryPoint
import io.bibuti.recycleradapter.BaseAdapter
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class ArticleListFragment :
    BaseFragment<FragmentArticleListBinding>(R.layout.fragment_article_list),
    BaseAdapter.BaseInterface {

    private val viewModel: ArticleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.callback = this@ArticleListFragment
        viewModel.getArticlesList()
        observeArticles()
    }

    private fun observeArticles() {
        viewModel.articleResult.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { response ->
                val result = response ?: return@Observer
                when {
                    result.isLoading -> Timber.w("Loading")
                    result.success -> {
                        binding.data = result.responseList
                    }
                    else -> {
                        showErrorSnack(result.message)
                    }
                }
            })
    }

    override fun onItemClicked(dataType: Any?, view: View?, position: Int) {
        val moveToSingleArticle =
            ArticleListFragmentDirections.moveToSingleArticle(articleData = (dataType as SingleArticleResponse))
        findNavController().navigate(moveToSingleArticle)
    }

}