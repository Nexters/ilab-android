package com.nexters.ilab.android.core.data.service

import com.nexters.ilab.android.core.data.response.StyleResponse
import retrofit2.http.GET

interface ILabService {
    @GET("style")
    suspend fun getStyleList(): List<StyleResponse>
}
