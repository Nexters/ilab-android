package com.nexters.ilab.android.feature.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.feature.intro.viewmodel.IntroSideEffect
import com.nexters.ilab.android.feature.intro.viewmodel.IntroViewModel
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.BackgroundImage
import timber.log.Timber

@Composable
internal fun IntroRoute(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is IntroSideEffect.ValidateToken -> {
                    if (AuthApiClient.instance.hasToken()) {
                        UserApiClient.instance.accessTokenInfo { _, error ->
                            if (error != null) {
                                if (error is KakaoSdkError && error.isInvalidTokenError()) {
                                    viewModel.autoLoginFail()
                                } else {
                                    Timber.e(error)
                                    viewModel.autoLoginFail()
                                }
                            } else {
                                viewModel.autoLoginSuccess()
                            }
                        }
                    }
                }
                is IntroSideEffect.AutoLoginSuccess -> navigateToMain()
                is IntroSideEffect.AutoLoginFail -> navigateToLogin()
            }
        }
    }

    IntroScreen()
}

@Composable
internal fun IntroScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage(
            resId = R.drawable.bg_splash_screen,
            contentDescription = "Background Image for Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        IntroContent(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun IntroContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "Splash Image",
            contentScale = ContentScale.Fit,
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_ilab_logo_64),
            contentDescription = "iLab Logo",
            tint = Color.Unspecified,
        )
    }
}

@DevicePreview
@Composable
fun IntroScreenPreview() {
    IntroScreen()
}
