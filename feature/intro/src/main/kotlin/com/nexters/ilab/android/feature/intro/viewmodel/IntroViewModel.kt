package com.nexters.ilab.android.feature.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repository: TokenRepository,
) : ViewModel(), ContainerHost<IntroState, IntroSideEffect> {

    override val container = container<IntroState, IntroSideEffect>(IntroState())

    init {
        validateToken()
    }

    private fun validateToken() = intent {
        postSideEffect(IntroSideEffect.ValidateToken)
    }

    fun autoLoginFail() = intent {
        postSideEffect(IntroSideEffect.AutoLoginFail)
    }

    fun autoLoginSuccess() = intent {
        postSideEffect(IntroSideEffect.AutoLoginSuccess)
    }

    private fun getAccessToken() = intent {
        viewModelScope.launch {
            delay(1000)
            val accessToken = repository.getAccessToken()
            if (accessToken.isNotEmpty()) {
                postSideEffect(IntroSideEffect.AutoLoginSuccess)
            } else {
                postSideEffect(IntroSideEffect.AutoLoginFail)
            }
        }
    }
}
