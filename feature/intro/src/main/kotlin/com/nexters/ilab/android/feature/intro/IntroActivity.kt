package com.nexters.ilab.android.feature.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.feature.navigator.LoginNavigator
import com.nexters.ilab.android.feature.navigator.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    private val viewModel: IntroViewModel by viewModels()

    @Inject
    lateinit var loginNavigator: LoginNavigator

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ILabTheme {
                IntroRoute(
                    navigateToLogin = {
                        loginNavigator.navigateFrom(
                            activity = this,
                            withFinish = true,
                        )
                    },
                    navigateToMain = {
                        mainNavigator.navigateFrom(
                            activity = this,
                            withFinish = true,
                        )
                    },
                    viewModel = viewModel,
                )
            }
        }
    }
}
