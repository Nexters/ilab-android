package com.nexters.ilab.android.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.common.UiText
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
class LoginViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val loginRepository: AuthRepository,
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun onLoginBtnClick() = intent {
        postSideEffect(LoginSideEffect.KakaoLogin)
    }

    fun kakaoLogin(accessToken: String, refreshToken: String, uuid: Long) = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            tokenRepository.setAccessToken(accessToken)
            tokenRepository.setRefreshToken(refreshToken)
            loginRepository.signIn()
                .onSuccess {
                    tokenRepository.setUUID(uuid)
                    postSideEffect(LoginSideEffect.LoginSuccess)
                }
                .onFailure { exception ->
                    Timber.e(exception)
                    tokenRepository.clearAuthToken()
                    postSideEffect(LoginSideEffect.LoginFail(exception))
                }
            reduce {
                state.copy(isLoading = false)
            }
        }
    }

    fun setErrorMessage(message: UiText) = intent {
        postSideEffect(LoginSideEffect.ShowToast(message))
    }
}
