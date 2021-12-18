package com.tejas.relifemedicalsystemtest.utils

import android.content.Context
import android.os.Environment
import com.tejas.relifemedicalsystemtest.R
import timber.log.Timber
import java.io.File

object Helpers {

    fun fetchDirPath(context: Context): File {
        val mediaStorageDir =
            File(Environment.DIRECTORY_PICTURES, "${context.getString(R.string.app_name)}/images")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Timber.d("failed to create directory")
            }
        }
        return mediaStorageDir
    }
}