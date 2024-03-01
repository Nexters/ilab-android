package com.nexters.ilab.android.core.data.service

import com.nexters.ilab.android.core.data.response.CreatedProfileResponse
import com.nexters.ilab.android.core.data.response.ProfileResponse
import com.nexters.ilab.android.core.data.response.StyleResponse
import com.nexters.ilab.android.core.data.response.UserInfoResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ILabService {
    @GET("style")
    suspend fun getStyleList(): List<StyleResponse>

    @GET("profiles")
    suspend fun getProfileList(): List<ProfileResponse>

    @Multipart
    @POST("upload")
    suspend fun createProfileImage(
        @Part file: MultipartBody.Part,
        @Part("styleId") styleId: Int,
        @Part("uuid") uuid: Long,
    ): List<CreatedProfileResponse>

    @POST("thumbnails/delete/{uuid}/{thumbnailId}")
    suspend fun deleteMyAlbumImage(
        @Path("uuid") uuid: Long,
        @Path("thumbnailId") thumbnailId: Int,
    ): UserInfoResponse
}
