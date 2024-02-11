package com.nexters.ilab.android.core.data.datasource

interface FileDataSource {
    suspend fun getImageUriList(createdImageUrls: List<String>): ArrayList<String>
    suspend fun saveImageFile(createdImageUrls: List<String>)
    fun deleteCacheDir()
}
