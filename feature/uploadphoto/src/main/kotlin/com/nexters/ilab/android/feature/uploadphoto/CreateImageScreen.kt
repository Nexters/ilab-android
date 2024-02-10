package com.nexters.ilab.android.feature.uploadphoto

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Blue600
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Gray100
import com.nexters.ilab.android.core.designsystem.theme.Gray500
import com.nexters.ilab.android.core.designsystem.theme.SystemRed
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.LoadingImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun CreateImageRoute(
    onCloseClick: () -> Unit,
    onNavigateToCreateImageComplete: () -> Unit,
    viewModel: UploadPhotoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    CreateImageScreen(
        uiState = uiState,
        onCloseClick = onCloseClick,
        onNavigateToCreateImageComplete = onNavigateToCreateImageComplete,
        openCreateImageStopDialog = viewModel::openCreateImageStopDialog,
        dismissCreateImageStopDialog = viewModel::dismissCreateImageStopDialog,
    )
}

@Composable
private fun CreateImageScreen(
    uiState: UploadPhotoState,
    onCloseClick: () -> Unit,
    onNavigateToCreateImageComplete: () -> Unit,
    openCreateImageStopDialog: () -> Unit,
    dismissCreateImageStopDialog: () -> Unit,
) {
    BackHandler {
        openCreateImageStopDialog()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (uiState.isCreateImageStopDialogVisible) {
            CreateImageStopDialog(
                onContinueClick = dismissCreateImageStopDialog,
                onConfirmClick = {
                    dismissCreateImageStopDialog()
                    onCloseClick()
                },
            )
        }
        CreateImageTopAppBar(onCloseClick = openCreateImageStopDialog)
        CreateImageContent(onNavigateToCreateImageComplete = onNavigateToCreateImageComplete)
    }
}

@Composable
private fun CreateImageTopAppBar(
    onCloseClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = null,
        navigationType = TopAppBarNavigationType.Close,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        onNavigationClick = onCloseClick,
    )
}

@Composable
private fun CreateImageContent(
    onNavigateToCreateImageComplete: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.creating_image_title),
            style = Title1,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Gray500)) {
                    append(stringResource(R.string.creating_image_wait_part1_description1_prefix))
                }
                withStyle(style = SpanStyle(color = Blue600, fontWeight = FontWeight.Bold)) {
                    append(stringResource(R.string.creating_image_wait_part1_description1_time_value))
                }
                withStyle(style = SpanStyle(color = Gray500)) {
                    append(stringResource(id = R.string.creating_image_wait_part1_description1_suffix))
                }
            },
            style = Contents1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.creating_image_wait_part1_description2),
            style = Contents1,
            color = Gray500,
        )
        LoadingImage(
            resId = R.drawable.anim_loading,
            contentDescription = "Loading image",
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Gray100)
                .clickable { onNavigateToCreateImageComplete() },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Gray500)) {
                            append(stringResource(R.string.creating_image_exit_warning_prefix))
                        }
                        withStyle(style = SpanStyle(color = SystemRed)) {
                            append(stringResource(id = R.string.creating_image_exit_warning_suffix))
                        }
                    },
                    style = Contents1,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@DevicePreview
@Composable
fun CreateImageScreenPreview() {
    CreateImageScreen(
        uiState = UploadPhotoState(),
        onCloseClick = {},
        onNavigateToCreateImageComplete = {},
        openCreateImageStopDialog = {},
        dismissCreateImageStopDialog = {},
    )
}
