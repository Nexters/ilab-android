package com.nexters.ilab.android.feature.home.viewmodel

sealed interface HomeSideEffect {
    data class OnCreateImageButtonClickFromStyle(val selectedStyle: String) : HomeSideEffect
    data class OnCreateImageButtonClickFromProfile(val selectedProfile: String) : HomeSideEffect
}
