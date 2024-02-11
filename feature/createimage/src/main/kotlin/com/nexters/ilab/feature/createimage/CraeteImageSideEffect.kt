package com.nexters.ilab.feature.createimage

sealed interface CreateImageSideEffect {
    data class ShareCreatedImage(val imageUriList: List<String>) : CreateImageSideEffect
    data object SaveCreatedImageSuccess : CreateImageSideEffect
}
