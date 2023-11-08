package com.example.qrstockmateapp.api.services

import com.example.qrstockmateapp.api.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("User/SignIn/")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}


data class LoginResponse(
    val user: User,
    val token: String
)
