package com.tejas.relifemedicalsystemtest.data.models

data class SignUpModel(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String = "",
)
