package com.tejas.relifemedicalsystemtest.data

interface SessionManager {
    var isLoggedIn: Boolean

    fun login(isLoggedIn: Boolean)
    fun logout()
}