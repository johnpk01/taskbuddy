package com.doseyenc.taskbuddy.data.model


data class LoginRequest(
    val username: String,
    val password: String
) {
    companion object {
        fun build(): LoginRequest {
            return LoginRequest(
                username = "365",
                password = "1"
            )
        }
    }
}