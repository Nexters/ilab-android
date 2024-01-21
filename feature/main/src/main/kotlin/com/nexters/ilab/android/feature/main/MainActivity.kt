package com.nexters.ilab.android.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO isDarkTheme 를 dataStore 에서 가져와서 구독하는 방식으로 수정
            val isDarkTheme = false
            val navigator: MainNavigator = rememberMainNavigator()

            ILabTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    navigator = navigator,
                    onChangeDarkTheme = { isDarkTheme -> viewModel.toggleDarkTheme(isDarkTheme) },
                )
            }
        }
    }
}
