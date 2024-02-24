package com.nexters.ilab.android.feature.setting.viewmodel

sealed interface SettingSideEffect {
    data object LogoutSuccess : SettingSideEffect
    data class LogoutFail(val throwable: Throwable) : SettingSideEffect
    data object DeleteAccountSuccess : SettingSideEffect
    data class DeleteAccountFail(val throwable: Throwable) : SettingSideEffect
}
