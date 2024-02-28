package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.response.CreatedProfileResponse

interface FileDataSource {
    suspend fun getImageUriList(createdImageUrls: List<String>): ArrayList<String>
    suspend fun saveImageFile(createdImageUrls: List<String>)
    fun deleteCacheDir()

    suspend fun createProfileImage(url: String, styleId: Int): List<CreatedProfileResponse>
}
