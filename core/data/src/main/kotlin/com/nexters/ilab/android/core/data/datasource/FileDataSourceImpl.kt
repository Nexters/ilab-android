package com.nexters.ilab.android.core.data.datasource

import com.nexters.ilab.android.core.data.util.FileUtil
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileUtil: FileUtil,
) : FileDataSource {
    override fun saveImageFile(fileName: String, byteArray: ByteArray): String {
        return fileUtil.saveImageFile(fileName, byteArray)
    }
}
