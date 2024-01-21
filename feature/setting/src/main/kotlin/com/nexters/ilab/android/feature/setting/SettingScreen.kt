package com.nexters.ilab.android.feature.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Suppress("unused")
@Composable
internal fun SettingRoute(
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    SettingScreen(
        onBackClick = onBackClick,
        onChangeDarkTheme = onChangeDarkTheme,
    )
}

@Suppress("unused")
@Composable
internal fun SettingScreen(
    onBackClick: () -> Unit,
    onChangeDarkTheme: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "SettingScreen")
    }
}
