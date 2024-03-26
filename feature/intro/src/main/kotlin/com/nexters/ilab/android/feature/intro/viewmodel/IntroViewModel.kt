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
import timber.log.Timber
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
        delay(1000)
        viewModelScope.launch {
            val accessToken = repository.getAccessToken()
            if (accessToken.isNotEmpty()) {
                Timber.d("accessToken: $accessToken")
                postSideEffect(IntroSideEffect.ValidateToken)
            } else {
                postSideEffect(IntroSideEffect.AutoLoginFail)
            }
        }
    }

    fun autoLoginFail() = intent {
        viewModelScope.launch {
            repository.clearAuthToken()
            postSideEffect(IntroSideEffect.AutoLoginFail)
        }
    }

    fun autoLoginSuccess() = intent {
        postSideEffect(IntroSideEffect.AutoLoginSuccess)
    }
}
