package com.nexters.ilab.android.core.domain.repository

interface FileRepository {
    fun saveImageFile(fileName: String, byteArray: ByteArray): String
}
