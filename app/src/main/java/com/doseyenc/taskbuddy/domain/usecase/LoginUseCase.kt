package com.doseyenc.taskbuddy.domain.usecase

import com.doseyenc.taskbuddy.data.model.LoginRequest
import com.doseyenc.taskbuddy.data.model.OAuth
import com.doseyenc.taskbuddy.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(request: LoginRequest): OAuth {
        return repository.login(request)
    }
}