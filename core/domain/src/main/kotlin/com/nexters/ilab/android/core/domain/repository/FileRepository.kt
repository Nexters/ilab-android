package com.nexters.ilab.android.core.domain.repository

import com.nexters.ilab.android.core.domain.entity.CreatedProfileEntity

interface FileRepository {
    suspend fun getImageUriList(createdImageUrls: List<String>): List<String>
    suspend fun saveImageFile(createdImageUrls: List<String>)
    fun deleteCacheDir()
    suspend fun createProfileImage(url: String, styleId: Int): Result<List<CreatedProfileEntity>>
}
