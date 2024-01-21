package com.nexters.ilab.android.feature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Suppress("unused")
@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    MyPageScreen(
        padding = padding,
        onSettingClick = onSettingClick,
    )
}

@Composable
internal fun MyPageScreen(
    padding: PaddingValues,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "MyPageScreen")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onSettingClick,
        ) {
            Text(text = "go to Setting")
        }
    }
}
