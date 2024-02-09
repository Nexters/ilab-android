package com.nexters.ilab.android.feature.login

import com.nexters.ilab.android.core.common.UiText

sealed interface LoginSideEffect {
    data object KakaoLogin : LoginSideEffect
    data object LoginSuccess : LoginSideEffect
    data class ShowToast(val message: UiText) : LoginSideEffect
}
