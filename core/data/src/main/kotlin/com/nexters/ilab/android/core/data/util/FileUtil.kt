package com.nexters.ilab.android.core.data.util

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileUtil @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val contentResolver = context.contentResolver
    private val contentValues = ContentValues()

    fun getImageUri(imageBytes: ByteArray): Uri {
        val imagesDir = File(context.cacheDir, "images")
        imagesDir.mkdirs()

        val imageFile = File(imagesDir, "IMG_${System.currentTimeMillis()}.png")
        imageFile.createNewFile()

        val fos = FileOutputStream(imageFile)
        fos.write(imageBytes)
        fos.close()

        return FileProvider.getUriForFile(context, context.packageName + ".provider", imageFile)
    }

    fun saveImageFile(fileName: String, byteArray: ByteArray): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageInQAndAbove(byteArray)
        } else {
            saveImageBelowQ(fileName, byteArray)
        }
    }

    fun deleteCacheDir() {
        val cacheDir = File(context.cacheDir, "images")
        cacheDir.deleteRecursively()
    }

    @Suppress("TooGenericExceptionThrown")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageInQAndAbove(byteArray: ByteArray): String {
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val item = contentResolver.insert(collection, contentValues)!!
        val pdf: ParcelFileDescriptor? = contentResolver.openFileDescriptor(item, "w", null)
        return pdf?.use {
            FileOutputStream(it.fileDescriptor).use { outputStream ->
                try {
                    outputStream.write(byteArray)
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(item, contentValues, null, null)
                    item.toString()
                } finally {
                    contentValues.clear()
                    outputStream.close()
                }
            }
        } ?: throw Exception("ParcelFileDescriptor is Null!", null)
    }

    private fun saveImageBelowQ(fileName: String, byteArray: ByteArray): String {
        val futureStudioIconFile =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "unsplash")
        if (!futureStudioIconFile.exists()) {
            futureStudioIconFile.mkdir()
        }
        val file = File(futureStudioIconFile, fileName)
        try {
            file.writeBytes(byteArray)
            with(contentValues) {
                put(MediaStore.Images.Media.TITLE, fileName)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.BUCKET_ID, fileName)
                put(MediaStore.Images.Media.DATA, file.absolutePath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/*")
            }
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
            return file.absoluteFile.toString()
        } finally {
            contentValues.clear()
        }
    }
}
