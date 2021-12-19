package com.tejas.relifemedicalsystemtest.ui.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.data.SessionManager
import com.tejas.relifemedicalsystemtest.databinding.FragmentArticleListBinding
import com.tejas.relifemedicalsystemtest.network.SingleArticleResponse
import com.tejas.relifemedicalsystemtest.ui.onboarding.OnBoardingActivity
import com.tejas.relifemedicalsystemtest.utils.clearTaskAndOpenActivity
import dagger.hilt.android.AndroidEntryPoint
import io.bibuti.recycleradapter.BaseAdapter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListFragment :
    BaseFragment<FragmentArticleListBinding>(R.layout.fragment_article_list),
    BaseAdapter.BaseInterface {

    private val viewModel: ArticleViewModel by viewModels()

    private val materialAlertDialogBuilder: MaterialAlertDialogBuilder by lazy {
        MaterialAlertDialogBuilder(requireContext())
    }

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.callback = this@ArticleListFragment
        viewModel.getArticlesList()

        binding.swipeRefreshFeeds.setOnRefreshListener {
            viewModel.getArticlesList()
        }

        binding.ivLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        observeArticles()
    }

    private fun observeArticles() {
        viewModel.articleResult.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { response ->
                val result = response ?: return@Observer
                when {
                    result.isLoading -> binding.pbArticles.show()
                    result.success -> {
                        binding.pbArticles.hide()
                        if (binding.swipeRefreshFeeds.isRefreshing) {
                            binding.swipeRefreshFeeds.isRefreshing = false
                        }
                        binding.noData = result.responseList.isEmpty()
                        binding.data = result.responseList
                    }
                    else -> {
                        binding.pbArticles.hide()
                        showErrorSnack(result.message)
                    }
                }
            })
    }

    private fun showLogoutConfirmation() {
        materialAlertDialogBuilder.setMessage(getString(R.string.desc_logout))
        materialAlertDialogBuilder.setCancelable(false)
        materialAlertDialogBuilder.setPositiveButton(android.R.string.ok) { d, _ ->
            d.dismiss()
            performLogout()
        }
        materialAlertDialogBuilder.setNegativeButton(android.R.string.cancel) { d, _ ->
            d.dismiss()
        }
        materialAlertDialogBuilder.show()
    }

    private fun performLogout() {
        sessionManager.logout()
        context?.clearTaskAndOpenActivity(OnBoardingActivity::class.java)
    }

    override fun onItemClicked(dataType: Any?, view: View?, position: Int) {
        val moveToSingleArticle =
            ArticleListFragmentDirections.moveToSingleArticle(
                articleData = (dataType as SingleArticleResponse)
            )
        findNavController().navigate(moveToSingleArticle)
    }

}