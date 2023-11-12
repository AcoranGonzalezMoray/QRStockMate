package com.example.qrstockmateapp.api.services

import com.example.qrstockmateapp.api.models.Company
import com.example.qrstockmateapp.api.models.User
import com.example.qrstockmateapp.api.models.Warehouse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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
    suspend fun signUp(
        @Body registrationBody: RegistrationBody
    ): Response<voidResponse>

    @POST("Warehouse/{id}")
    suspend fun createWarehouse(
        @Path("id") id: Int,
        @Body warehouse: Warehouse
    ): Response<voidResponse>
    @PUT("Warehouse/")
    suspend fun updateWarehouse(
        @Body warehouse: Warehouse
    ): Response<voidResponse>

    @HTTP(method = "DELETE", path = "/User/DeleteAccount", hasBody = true) //Para un Delete con Body hay que hacerlo asi
    suspend fun deleteAccount(
        @Body user: User
    ): Response<Unit>

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
