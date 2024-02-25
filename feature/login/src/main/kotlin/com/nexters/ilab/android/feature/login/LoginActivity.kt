package com.nexters.ilab.android.feature.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.feature.login.viewmodel.LoginViewModel
import com.nexters.ilab.android.feature.navigator.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberExSystemUiController()

            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = true,
                    isNavigationBarContrastEnforced = false,
                )

                onDispose {}
            }

            ILabTheme {
                LoginRoute(
                    navigateToHome = {
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
