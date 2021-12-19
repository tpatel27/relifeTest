package com.tejas.relifemedicalsystemtest.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

class SharedPref(val context: Context) {

    private val prefName = "user_phone_pref"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    fun savePhoneNumber(list: MutableList<String>, key: String) {
        val phoneDataJson: String = Gson().toJson(list)
        preferences.edit {
            putString(key, phoneDataJson)
            apply()
        }
    }

    fun getPhoneNumbers(key: String): MutableList<String> {
        val json: String = preferences.getString(key, null) ?: ""
        val type: Type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(json, type) ?: mutableListOf()
    }

    fun clearSharedPref() {
        preferences.edit {
            clear()
            apply()
        }
    }
}