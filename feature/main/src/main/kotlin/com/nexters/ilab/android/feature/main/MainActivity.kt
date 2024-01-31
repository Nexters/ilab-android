package com.nexters.ilab.android.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.feature.navigator.LoginNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var loginNavigator: LoginNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO isDarkTheme 를 dataStore 에서 가져와서 구독하는 방식으로 수정
            val isDarkTheme = false
            val navigator: MainNavController = rememberMainNavController()

            WindowCompat.setDecorFitsSystemWindows(window, false)

            ILabTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    onChangeDarkTheme = { isDarkTheme -> viewModel.toggleDarkTheme(isDarkTheme) },
                    onLogoutClick = {
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
