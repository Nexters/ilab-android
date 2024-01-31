package com.nexters.ilab.android.core.common.extension

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

fun Bitmap.toUri(context: Context): Uri {
    val filename = "${System.currentTimeMillis()}.png"
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
    }

    val imageUri = context.contentResolver.insert(imageCollection, contentValues)
    imageUri?.let { uri ->
        context.contentResolver.openOutputStream(uri).use { outputStream ->
            if (outputStream != null) {
                this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
        }
        return uri
    } ?: error("Failed to create new MediaStore record.")
}
