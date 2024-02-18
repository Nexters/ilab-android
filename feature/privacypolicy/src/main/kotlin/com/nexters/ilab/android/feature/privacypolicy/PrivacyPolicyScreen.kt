package com.nexters.ilab.android.feature.privacypolicy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun PrivacyPolicyRoute(
    onCloseClick: () -> Unit,
    viewModel: PrivacyPolicyViewModel = hiltViewModel(),
) {
    val webViewState = viewModel.webViewState

    PrivacyPolicyScreen(
        onCloseClick = onCloseClick,
        webViewState = webViewState,
    )
}

@Composable
internal fun PrivacyPolicyScreen(
    onCloseClick: () -> Unit,
    webViewState: WebViewState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        PrivacyPolicyTopAppBar(onCloseClick = onCloseClick)
        PrivacyPolicyContent(webViewState = webViewState)
    }
}

@Composable
internal fun PrivacyPolicyContent(webViewState: WebViewState) {
    WebView(
        state = webViewState,
        onCreated = { webView ->
            webView.apply {
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }
            }
        },
    )
}

@Composable
internal fun PrivacyPolicyTopAppBar(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ILabTopAppBar(
        titleRes = R.string.privacy_policy_title,
        navigationType = TopAppBarNavigationType.Close,
        onNavigationClick = onCloseClick,
        navigationIconContentDescription = null,
        modifier = modifier
            .statusBarsPadding()
            .height(56.dp),
    )
}
