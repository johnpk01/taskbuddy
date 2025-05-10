package com.doseyenc.taskbuddy.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("oauth")
    val oauth: OAuth
)

data class OAuth(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("expires_in")
    val expiresIn: Int,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("refresh_token")
    val refreshToken: String
)