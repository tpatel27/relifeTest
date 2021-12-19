package com.tejas.relifemedicalsystemtest.ui.articles

import android.Manifest
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.databinding.FragmentSingleArticleBinding
import com.tejas.relifemedicalsystemtest.utils.Constants
import com.tejas.relifemedicalsystemtest.utils.Helpers
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.R)
@AndroidEntryPoint
class SingleArticleFragment :
    BaseFragment<FragmentSingleArticleBinding>(R.layout.fragment_single_article) {

    private val singleArticleArgs by navArgs<SingleArticleFragmentArgs>()
    private var fileName = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.article = singleArticleArgs.articleData

        fileName = "${singleArticleArgs.articleData.title.replace(" ", "_")}.jpg"

        binding.tvSingleArticleSource.setOnClickListener {
            launchSource(singleArticleArgs.articleData.url)
        }

        binding.ivDownloadImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mediaStoreAction()
            } else {
                downloadImage(singleArticleArgs.articleData.imageUrl)
            }

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.d("Granted")
                    Helpers.storeAsBitmap(
                        context = requireActivity(),
                        url = singleArticleArgs.articleData.imageUrl,
                        imageName = fileName
                    )
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_MEDIA_LOCATION)) {
                        showPermissionDeniedDialog(Manifest.permission.ACCESS_MEDIA_LOCATION)
                    } else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_MEDIA_LOCATION)) {
                            Timber.d("Permission denied permanent")
                        }
                    }
                }
            }
        }
    }

    private fun mediaStoreAction() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           Timber.d("Already granted")
            Helpers.storeAsBitmap(
                context = requireActivity(),
                url = singleArticleArgs.articleData.imageUrl,
                imageName = fileName
            )
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_MEDIA_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                //Permission is denied can show some alert here
                showPermissionDeniedDialog(Manifest.permission.CAMERA)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), Constants.REQUEST_STORAGE)
            }
        }
    }

    private fun showPermissionDeniedDialog(permissions: String) {
        AlertDialog.Builder(requireActivity()).apply {
            setCancelable(true)
            setMessage("Give Permissions")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permissions), Constants.REQUEST_STORAGE)
            }
        }.show()
    }
}