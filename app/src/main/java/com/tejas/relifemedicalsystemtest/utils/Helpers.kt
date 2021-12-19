@file:Suppress("BlockingMethodInNonBlockingContext")

package com.tejas.relifemedicalsystemtest.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.tejas.relifemedicalsystemtest.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.File
import java.io.OutputStream

object Helpers {

    fun fetchDirPath(context: Context): File {
        val dir =
            File(Environment.DIRECTORY_PICTURES, "${context.getString(R.string.app_name)}/images")
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Timber.e("failed to create directory")
            }
        }
        return dir
    }

    fun storeInSdkQ(context: Context, url: String, imageName: String): String {
        val outputStream: OutputStream
        var result = ""
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val path =
                    "${Environment.DIRECTORY_PICTURES}${File.separator}${context.getString(R.string.app_name)}/images"
                val resolver: ContentResolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, path)
                val uri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = resolver.openOutputStream(uri!!)!!
                GlobalScope.launch {
                    val bmp = createBitmapFromUrl(url)
                    bmp?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                result = "File downloaded at $path"
            }
        } catch (e: Exception) {
            Timber.e(e)
            result = "Unexpected error occurred while downloading"
        }
        return result
    }

    private suspend fun createBitmapFromUrl(url: String): Bitmap? = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val req = Request.Builder().url(url).build()
        val res = client.newCall(req).execute()
        BitmapFactory.decodeStream(res.body?.byteStream())
    }
}