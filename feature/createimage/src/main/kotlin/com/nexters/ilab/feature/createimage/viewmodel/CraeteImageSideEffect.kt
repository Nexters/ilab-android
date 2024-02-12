package com.nexters.ilab.feature.createimage.viewmodel

sealed interface CreateImageSideEffect {
    data class ShareCreatedImage(val imageUriList: List<String>) : CreateImageSideEffect
    data object SaveCreatedImageSuccess : CreateImageSideEffect
}
