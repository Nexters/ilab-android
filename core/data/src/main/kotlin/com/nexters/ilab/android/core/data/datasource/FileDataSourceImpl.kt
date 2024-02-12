package com.nexters.ilab.android.core.data.datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.nexters.ilab.android.core.data.util.FileUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileUtil: FileUtil,
) : FileDataSource {
    override suspend fun getImageUriList(createdImageUrls: List<String>): ArrayList<String> {
        val imageByteArrayList = createByteArrayListFromUrls(createdImageUrls)
        val imageUriList = ArrayList<String>()
        for (imageBytes in imageByteArrayList) {
            val imageUri = fileUtil.getImageUri(imageBytes)
            imageUriList.add(imageUri.toString())
        }
        return imageUriList
    }

    override suspend fun saveImageFile(createdImageUrls: List<String>) {
        val imageInfoList = createImageInfoListFromUrls(createdImageUrls)
        imageInfoList.forEach { (fileName, byteArray) ->
            fileUtil.saveImageFile(
                fileName = fileName,
                byteArray = byteArray,
            )
        }
    }

    override fun deleteCacheDir() {
        fileUtil.deleteCacheDir()
    }

    private suspend fun createImageInfoListFromUrls(createdImageUrls: List<String>): List<Pair<String, ByteArray>> {
        val imageLoader = ImageLoader(context)

        return createdImageUrls.map { url ->
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()

            val result = (imageLoader.execute(request) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            Pair("IMG_${System.currentTimeMillis()}.png", stream.toByteArray())
        }
    }

    private suspend fun createByteArrayListFromUrls(createdImageUrls: List<String>): List<ByteArray> {
        val imageLoader = ImageLoader(context)

        return createdImageUrls.map { url ->
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()

            val result = (imageLoader.execute(request) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            stream.toByteArray()
        }
    }
}
