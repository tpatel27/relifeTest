package com.tejas.relifemedicalsystemtest.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import com.tejas.relifemedicalsystemtest.R
import timber.log.Timber
import java.io.File
import android.graphics.BitmapFactory
import android.media.MediaFormat
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


object Helpers {

    fun fetchDirPath(context: Context): File {
        val high = Build.VERSION.SDK_INT <= Build.VERSION_CODES.R
        val dir = File(Environment.DIRECTORY_PICTURES, "${context.getString(R.string.app_name)}/images")
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Timber.e("failed to create directory")
            }
        }
        return dir
    }

    fun storeAsBitmap(context: Context, url: String, imageName: String){
        val outputStream: OutputStream
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver: ContentResolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, fetchDirPath(context).absolutePath)
                val uri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = resolver.openOutputStream(uri!!)!!
                val bmp = getBitmapFromURL(url)
                bmp?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val downloadSuccess =
                    "File downloaded at ${fetchDirPath(context).absolutePath}"
                Toast.makeText(context, downloadSuccess, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

//    private fun fetchBitmap(url: String):Bitmap {
//        try {
//            return bmp = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
//        } catch (e: IOException) {
//            Timber.e(e)
//            return bmp
//        }
//    }

    private fun getBitmapFromURL(src: String): Bitmap? {
        val connection: HttpURLConnection
        val url = URL(src)
        return try {
            connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}