package com.nexters.ilab.android.core.data.service

import com.nexters.ilab.android.core.data.request.SignInRequest
import com.nexters.ilab.android.core.data.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("users/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest)

    @POST("users/me/{uuid}")
    suspend fun getUserInfo(
        @Path("uuid") uuid: Long,
    ): UserInfoResponse

    @POST("users/logout/{uuid}")
    suspend fun signOut(
        @Path("uuid") uuid: Long,
    )

    @POST("users/delete/{uuid}")
    suspend fun deleteAccount(
        @Path("uuid") uuid: Long,
    )
}
