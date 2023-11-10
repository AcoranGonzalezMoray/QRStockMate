package com.example.qrstockmateapp.api.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val code: String,
    val url: String,
    val role: Int,

)

fun userRoleToString(roleId: Int): String {
    return when (roleId) {
        0 -> "Director"
        1 -> "Administrator"
        2 -> "Inventory Technician"
        3 -> "User"
        else -> "Unknown Role"
    }
}
