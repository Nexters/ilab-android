package com.nexters.ilab.android.feature.login

import androidx.lifecycle.ViewModel
import com.nexters.ilab.android.core.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun onLoginButtonClick() = intent {
        postSideEffect(LoginSideEffect.KakaoLogin)
    }

    fun kakaoLogin() = intent {
        reduce {
            state.copy(isLoading = true)
        }
        // TODO repository function call
        reduce {
            state.copy(isLoading = false)
        }
        postSideEffect(LoginSideEffect.LoginSuccess)
    }

    fun setErrorMessage(message: UiText) = intent {
        postSideEffect(LoginSideEffect.ShowToast(message))
    }
}
