package com.doseyenc.taskbuddy.data.remote

import com.doseyenc.taskbuddy.data.model.LoginRequest
import com.doseyenc.taskbuddy.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApiService {

    @Headers(
        "Authorization: Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz",
        "Content-Type: application/json"
    )
    @POST("index.php/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}