package com.nexters.ilab.android.core.data.service

import com.nexters.ilab.android.core.data.request.AuthRequest
import com.nexters.ilab.android.core.data.request.SignInRequest
import com.nexters.ilab.android.core.data.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("users/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest)

    @POST("users/me")
    suspend fun getUserInfo(@Body authRequest: AuthRequest): UserInfoResponse

    @POST("users/logout")
    suspend fun signOut(@Body authRequest: AuthRequest)

    @POST("users/delete")
    suspend fun deleteAccount(@Body authRequest: AuthRequest)
}
