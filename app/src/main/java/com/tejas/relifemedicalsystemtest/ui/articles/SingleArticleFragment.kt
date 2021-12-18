package com.tejas.relifemedicalsystemtest.ui.articles

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.databinding.FragmentSingleArticleBinding
import com.tejas.relifemedicalsystemtest.utils.Helpers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleArticleFragment :
    BaseFragment<FragmentSingleArticleBinding>(R.layout.fragment_single_article) {

    private val singleArticleArgs by navArgs<SingleArticleFragmentArgs>()

    private var fileName = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.article = singleArticleArgs.articleData

        fileName = "${singleArticleArgs.articleData.title.replace(" ", "_")}.jpg"

        binding.ivDownloadImage.setOnClickListener {
            downloadImage(singleArticleArgs.articleData.imageUrl)
        }

        binding.ivBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun downloadImage(url: String) {
        requireActivity().runWithPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            val request: DownloadManager.Request = DownloadManager.Request(
                Uri.parse(url)
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Helpers.fetchDirPath(requireActivity()).absolutePath,
                fileName
            )
            val dm: DownloadManager =
                requireActivity().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            val downloadSuccess =
                "File downloaded at ${Environment.DIRECTORY_PICTURES}/${getString(R.string.app_name)}/images"
            Toast.makeText(requireActivity(), downloadSuccess, Toast.LENGTH_LONG).show()
        }
    }
}