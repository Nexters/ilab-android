package com.nexters.ilab.android.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kakao.sdk.common.KakaoSdk
import com.nexters.ilab.android.BuildConfig

class KakaoSDKInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        KakaoSdk.init(context, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
