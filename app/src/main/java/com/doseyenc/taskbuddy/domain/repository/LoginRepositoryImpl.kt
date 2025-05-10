package com.doseyenc.taskbuddy.domain.repository

import com.doseyenc.taskbuddy.data.model.LoginRequest
import com.doseyenc.taskbuddy.data.model.OAuth
import com.doseyenc.taskbuddy.data.remote.LoginApiService

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService
) : LoginRepository {
    override suspend fun login(request: LoginRequest): OAuth {
        return loginApiService.login(request).oauth
    }
}