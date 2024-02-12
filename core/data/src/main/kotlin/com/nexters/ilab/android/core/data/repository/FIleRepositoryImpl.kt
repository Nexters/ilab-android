package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.FileDataSource
import com.nexters.ilab.android.core.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
) : FileRepository {
    override suspend fun getImageUriList(createdImageUrls: List<String>): List<String> {
        return fileDataSource.getImageUriList(createdImageUrls)
    }

    override suspend fun saveImageFile(createdImageUrls: List<String>) {
        return fileDataSource.saveImageFile(createdImageUrls)
    }

    override fun deleteCacheDir() {
        fileDataSource.deleteCacheDir()
    }
}
