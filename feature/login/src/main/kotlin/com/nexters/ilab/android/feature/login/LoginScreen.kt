package com.nexters.ilab.android.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import com.nexters.ilab.android.core.common.UiText
import com.nexters.ilab.android.core.designsystem.R
import timber.log.Timber

@Composable
internal fun LoginRoute(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> when {
                (error is AuthError && error.response.error == "ProtocolError") -> {
                    Timber.e("로그인 실패: ${error.response.error}, ${error.response.errorDescription}")
                    viewModel.setErrorMessage(UiText.StringResource(R.string.error_message_network))
                }

                else -> {
                    Timber.e("로그인 실패: ${error.message}")
                    viewModel.setErrorMessage(UiText.StringResource(R.string.error_message_unknown))
                }
            }

            token != null -> UserApiClient.instance.me { user, _ ->
                user?.let {
                    Timber.d("로그인 성공: ${token.accessToken}, ${it.kakaoAccount?.name}, ${it.kakaoAccount?.profile?.profileImageUrl}")
                    viewModel.kakaoLogin()
                } ?: viewModel.setErrorMessage(UiText.StringResource(R.string.error_message_unknown))
            }

            else -> viewModel.setErrorMessage(UiText.StringResource(R.string.error_message_unknown))
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.KakaoLogin -> {
                    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                        UserApiClient.instance.loginWithKakaoTalk(context, callback = kakaoCallback)
                    } else {
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
                    }
                }

                is LoginSideEffect.LoginSuccess -> navigateToHome()
                is LoginSideEffect.ShowToast -> Toast.makeText(context, sideEffect.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onLoginClick = viewModel::onLoginButtonClick,
    )
}

@Composable
internal fun LoginScreen(
    uiState: LoginState,
    onLoginClick: () -> Unit,
) {
    if (uiState.isLoading) {
        CircularProgressIndicator()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "LoginScreen")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onLoginClick,
        ) {
            Text(text = stringResource(id = R.string.kakao_login))
        }
    }
}
