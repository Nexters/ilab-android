package com.nexters.ilab.android.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import com.nexters.ilab.android.core.common.UiText
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Gray900
import com.nexters.ilab.android.core.designsystem.theme.Kakao
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.BackgroundImage
import com.nexters.ilab.core.ui.component.ILabButton
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
                    Timber.d("로그인 성공: ${token.accessToken}, ${it.kakaoAccount?.profile?.nickname}, ${it.kakaoAccount?.profile?.profileImageUrl}")
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

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = R.drawable.bg_login_screen,
            contentDescription = "Background Image for Login Screen",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        LoginContent(
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
fun LoginContent(
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(horizontal = 21.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_ilab_logo_110),
                contentDescription = "iLab Logo",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.intro_description),
                style = Contents1,
            )
        }
        Spacer(modifier = Modifier.weight(2f))
        ILabButton(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 34.dp)
                .height(60.dp),
            containerColor = Kakao,
            contentColor = Gray900,
            text = {
                Text(
                    text = stringResource(id = R.string.kakao_login),
                    fontSize = 18.sp,
                    style = Subtitle1,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_kakao),
                    contentDescription = "Kakao Icon",
                    tint = Color.Unspecified,
                )
            },
        )
    }
}

@DevicePreview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginState(),
        onLoginClick = {},
    )
}
