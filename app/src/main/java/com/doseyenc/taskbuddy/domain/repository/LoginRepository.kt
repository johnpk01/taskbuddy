package com.doseyenc.taskbuddy.domain.repository

import com.doseyenc.taskbuddy.data.model.LoginRequest
import com.doseyenc.taskbuddy.data.model.OAuth

interface LoginRepository {
    suspend fun login(request: LoginRequest): OAuth
}