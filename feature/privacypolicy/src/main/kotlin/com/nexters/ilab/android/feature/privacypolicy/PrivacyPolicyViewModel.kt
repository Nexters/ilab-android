package com.nexters.ilab.android.feature.privacypolicy

import androidx.lifecycle.ViewModel
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor() : ViewModel() {
    val webViewState = WebViewState(WebContent.Url(url = "https://m.naver.com"))
}
