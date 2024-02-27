package com.nexters.ilab.android.feature.intro.viewmodel

sealed interface IntroSideEffect {
    data object AutoLoginSuccess : IntroSideEffect
    data object AutoLoginFail : IntroSideEffect

}
