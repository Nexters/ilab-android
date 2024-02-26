package com.nexters.ilab.android.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.feature.navigator.LoginNavigator
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            // TODO isDarkTheme 를 dataStore 에서 가져와서 구독하는 방식으로 수정
            // val isDarkTheme = false
            val navigator: MainNavController = rememberMainNavController()
            val systemUiController = rememberExSystemUiController()

            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.White,
                    darkIcons = true,
                    isNavigationBarContrastEnforced = false,
                )

                onDispose {}
            }

            ILabTheme {
                MainScreen(
                    onChangeDarkTheme = { isDarkTheme -> viewModel.toggleDarkTheme(isDarkTheme) },
                    onNavigateToLogin = {
                        loginNavigator.navigateFrom(
                            activity = this,
                            withFinish = true,
                        )
                    },
                    navigator = navigator,
                )
            }
        }
    }
}
