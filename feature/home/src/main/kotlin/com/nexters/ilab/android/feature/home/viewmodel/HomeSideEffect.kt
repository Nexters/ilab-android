package com.nexters.ilab.android.feature.home.viewmodel

sealed interface HomeSideEffect {
    data class NavigateToUploadPhoto(val selectedStyle: String) : HomeSideEffect
}
