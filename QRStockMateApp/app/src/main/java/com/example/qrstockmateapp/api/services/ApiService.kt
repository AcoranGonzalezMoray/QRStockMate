package com.example.qrstockmateapp.api.services

import com.example.qrstockmateapp.api.models.Company
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.models.Warehouse
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

    @POST("User/Company/")
    suspend fun getCompanyByUser(
        @Body user: User
    ): Response<Company>

    @POST("Company/Warehouse/")
    suspend fun getWarehouse(
        @Body company: Company
    ): Response<List<Warehouse>>

    @POST("Company/Employees/")
    suspend fun getEmployees(
        @Body company: Company
    ): Response<List<User>>

    @POST("User/SignUp")
    suspend fun joinWithCode(
        @Body registrationBody: RegistrationBody
    ): Response<voidResponse>
}


data class LoginResponse(
    val user: User,
    val token: String
)
data class RegistrationBody(
    val user:User,
    val company: Company
)

data class voidResponse(
    val success: Boolean,
    val message: String? // Mensaje descriptivo opcional
)
