package com.nexters.ilab.android.core.data.service

import com.nexters.ilab.android.core.data.request.SignInRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("users/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest)

    @POST("users/logout")
    suspend fun signOut()

    @POST("users/delete")
    suspend fun deleteAccount()
}
