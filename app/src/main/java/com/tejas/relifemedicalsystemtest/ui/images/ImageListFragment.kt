package com.tejas.relifemedicalsystemtest.ui.images

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.tejas.relifemedicalsystemtest.BuildConfig
import com.tejas.relifemedicalsystemtest.R
import com.tejas.relifemedicalsystemtest.core.BaseFragment
import com.tejas.relifemedicalsystemtest.databinding.FragmentImageListBinding
import com.tejas.relifemedicalsystemtest.network.HitsItem
import com.tejas.relifemedicalsystemtest.ui.callbacks.OnDialogListeners
import com.tejas.relifemedicalsystemtest.utils.Constants
import com.tejas.relifemedicalsystemtest.utils.Helpers
import com.tejas.relifemedicalsystemtest.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import io.bibuti.recycleradapter.BaseAdapter
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.R)
@AndroidEntryPoint
class ImageListFragment : BaseFragment<FragmentImageListBinding>(R.layout.fragment_image_list),
    BaseAdapter.BaseInterface, OnDialogListeners {

    private val viewModel: ImagesViewModel by viewModels()

    private val searchTerm = "Medical Images"

    private var imageUrl = ""

    private val materialAlertDialogBuilder: MaterialAlertDialogBuilder by lazy {
        MaterialAlertDialogBuilder(requireContext())
    }

    private var fileName = "MCP_${Helpers.getToday(pattern = Constants.fileSaveDatePattern)}.jpg"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getImages(
            apiKey = BuildConfig.USER_IMAGE_API_KEY,
            query = searchTerm
        )

        binding.callback = this@ImageListFragment

        binding.ivGalleryBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        observeImages()
    }

    private fun observeImages() {
        viewModel.imageResult.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { response ->
                val result = response ?: return@Observer
                when {
                    result.isLoading -> binding.pbImages.show()
                    result.success -> {
                        binding.pbImages.hide()
                        binding.noData = result.response?.hits?.isEmpty()
                        binding.data = result.response?.hits
                    }
                    else -> {
                        binding.pbImages.hide()
                        showErrorSnack(result.message)
                    }
                }
            })
    }

    private fun downloadImage() {
        requireActivity().runWithPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
            val request: DownloadManager.Request = DownloadManager.Request(
                Uri.parse(imageUrl)
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Helpers.fetchDirPath(requireActivity()).absolutePath,
                fileName
            )
            val dm: DownloadManager =
                requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            val downloadSuccess =
                "File downloaded at ${Environment.DIRECTORY_PICTURES}/${getString(R.string.app_name)}/images"
            context?.toast(downloadSuccess)
        }
    }

    private fun mediaStoreAction() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_MEDIA_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            context?.toast(
                Helpers.storeInSdkQ(
                    context = requireActivity(),
                    url = imageUrl,
                    imageName = fileName
                )
            )
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_MEDIA_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                )
            ) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION),
                    Constants.REQUEST_STORAGE
                )
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        materialAlertDialogBuilder.setMessage(getString(R.string.desc_provide_permissions))
        materialAlertDialogBuilder.setCancelable(true)
        materialAlertDialogBuilder.setPositiveButton(android.R.string.ok) { d, _ ->
            d.dismiss()
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION),
                Constants.REQUEST_STORAGE
            )
        }
        materialAlertDialogBuilder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    context?.toast(
                        Helpers.storeInSdkQ(
                            context = requireActivity(),
                            url = imageUrl,
                            imageName = fileName
                        )
                    )
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.ACCESS_MEDIA_LOCATION
                        )
                    ) {
                        showPermissionDeniedDialog()
                    } else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                requireActivity(),
                                Manifest.permission.ACCESS_MEDIA_LOCATION
                            )
                        ) {
                            Timber.d("Permission denied permanently")
                        }
                    }
                }
            }
        }
    }

    override fun onImageDownloadListener() {
        super.onImageDownloadListener()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mediaStoreAction()
        } else {
            downloadImage()
        }
    }

    override fun onItemClicked(dataType: Any?, view: View?, position: Int) {
        val item = (dataType as HitsItem)
        imageUrl = item.largeImageURL ?: ""
        val fullScreenViewer = FullScreenImageFragment(
            listener = this@ImageListFragment,
            imageUrl = imageUrl
        )
        fullScreenViewer.show(childFragmentManager, fullScreenViewer.tag)
    }
}