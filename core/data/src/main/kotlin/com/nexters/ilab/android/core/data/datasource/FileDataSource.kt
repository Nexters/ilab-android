package com.nexters.ilab.android.core.data.datasource

interface FileDataSource {
    fun saveImageFile(fileName: String, byteArray: ByteArray): String
}
