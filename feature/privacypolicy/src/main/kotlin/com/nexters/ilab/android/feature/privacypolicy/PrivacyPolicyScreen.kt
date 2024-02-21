package com.nexters.ilab.android.feature.privacypolicy

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun PrivacyPolicyRoute(
    onCloseClick: () -> Unit,
) {
    PrivacyPolicyScreen(
        onCloseClick = onCloseClick,
        privacyPolicyWebViewUrl = BuildConfig.PRIVACY_POLICY_WEB_VIEW_URL,
    )
}

@Composable
internal fun PrivacyPolicyScreen(
    onCloseClick: () -> Unit,
    privacyPolicyWebViewUrl: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        PrivacyPolicyTopAppBar(onCloseClick = onCloseClick)
        PrivacyPolicyContent(privacyPolicyWebViewUrl = privacyPolicyWebViewUrl)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun PrivacyPolicyContent(
    privacyPolicyWebViewUrl: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                    }
                    loadUrl(privacyPolicyWebViewUrl)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
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
