package com.nexters.ilab.android.core.domain.repository

interface FileRepository {
    suspend fun getImageUriList(createdImageUrls: List<String>): List<String>
    suspend fun saveImageFile(createdImageUrls: List<String>)
    fun deleteCacheDir()
}
