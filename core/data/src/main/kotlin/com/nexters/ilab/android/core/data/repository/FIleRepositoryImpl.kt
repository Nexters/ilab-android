package com.nexters.ilab.android.core.data.repository

import com.nexters.ilab.android.core.data.datasource.FileDataSource
import com.nexters.ilab.android.core.domain.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
) : FileRepository {
    override fun saveImageFile(fileName: String, byteArray: ByteArray): String {
        return fileDataSource.saveImageFile(fileName, byteArray)
    }
}
