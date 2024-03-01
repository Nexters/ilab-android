package com.nexters.ilab.android.feature.home.viewmodel

sealed interface HomeSideEffect {
    data class OnCreateImageBtnClickFromStyle(val selectedStyle: String) : HomeSideEffect
    data class OnCreateImageBtnClickFromProfile(val selectedProfile: String) : HomeSideEffect
}
