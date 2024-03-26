package com.nexters.ilab.android.feature.setting.viewmodel

import android.content.Context
import android.content.pm.PackageInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.domain.repository.AuthRepository
import com.nexters.ilab.android.core.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel(), ContainerHost<SettingState, SettingSideEffect> {

    override val container = container<SettingState, SettingSideEffect>(SettingState())

    fun signOut() = intent {
        viewModelScope.launch {
            reduce { state.copy(isLoading = true) }
            authRepository.signOut()
                .onSuccess {
                    tokenRepository.clearAuthToken()
                    postSideEffect(SettingSideEffect.LogoutSuccess)
                }.onFailure { exception ->
                    Timber.d("$exception")
                    postSideEffect(SettingSideEffect.LogoutFail(exception))
                }
            reduce { state.copy(isLoading = false) }
        }
    }

    fun getVersionInfo(context: Context): String {
        val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (packageInfo != null) {
            return packageInfo.versionName
        } else {
            return "버전 정보가 없습니다."
        }
    }

    fun deleteAccount() = intent {
        viewModelScope.launch {
            viewModelScope.launch {
                reduce { state.copy(isLoading = true) }
                authRepository.deleteAccount()
                    .onSuccess {
                        tokenRepository.clearAuthToken()
                        postSideEffect(SettingSideEffect.LogoutSuccess)
                    }.onFailure { exception ->
                        Timber.d("$exception")
                        postSideEffect(SettingSideEffect.DeleteAccountFail(exception))
                    }
                reduce { state.copy(isLoading = false) }
            }
        }
    }

    fun openDeleteAccountDialog() = intent {
        reduce {
            state.copy(isDeleteAccountDialogVisible = true)
        }
    }

    fun dismissDeleteAccountDialog() = intent {
        reduce {
            state.copy(isDeleteAccountDialogVisible = false)
        }
    }
}
