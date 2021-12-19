package com.tejas.relifemedicalsystemtest.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.removeObservers(this)
    liveData.observe(this, observer)
}

fun <T> Context.clearTaskAndOpenActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Context.toast(message:String){
    Toast.makeText(this, message , Toast.LENGTH_LONG).show()
}