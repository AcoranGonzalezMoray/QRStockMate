package com.example.qrstockmateapp.api.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val code: String,
    val url: String,
    val role: String,

)
