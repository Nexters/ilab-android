package com.nexters.ilab.feature.createimage

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.common.UiText
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabDialog
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.LoadingImage
import com.nexters.ilab.core.ui.component.NetworkErrorDialog
import com.nexters.ilab.core.ui.component.ServerErrorDialog
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageSideEffect
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageState
import com.nexters.ilab.feature.createimage.viewmodel.CreateImageViewModel

@Composable
internal fun CreateImageRoute(
    onCloseClick: () -> Unit,
    onNavigateToCreateImageComplete: () -> Unit,
    viewModel: CreateImageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is CreateImageSideEffect.CreateProfileImageSuccess -> {
                    onNavigateToCreateImageComplete()
                }

                else -> {}
            }
        }
    }

    CreateImageScreen(
        uiState = uiState,
        onCloseClick = onCloseClick,
        onNavigateToCreateImageComplete = onNavigateToCreateImageComplete,
        openCreateImageStopDialog = viewModel::openCreateProfileImageStopDialog,
        dismissCreateImageStopDialog = viewModel::dismissCreateProfileImageStopDialog,
        createProfileImage = viewModel::createProfileImage,
        dismissServerErrorDialog = viewModel::dismissServerErrorDialog,
        dismissNetworkErrorDialog = viewModel::dismissNetworkErrorDialog,
    )
}

@Composable
private fun CreateImageScreen(
    uiState: CreateImageState,
    onCloseClick: () -> Unit,
    onNavigateToCreateImageComplete: () -> Unit,
    openCreateImageStopDialog: () -> Unit,
    dismissCreateImageStopDialog: () -> Unit,
    createProfileImage: () -> Unit,
    dismissServerErrorDialog: () -> Unit,
    dismissNetworkErrorDialog: () -> Unit,
) {
    BackHandler {
        openCreateImageStopDialog()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (uiState.isCreateImageStopDialogVisible) {
            CreateImageStopDialog(
                onCancelClick = {
                    dismissCreateImageStopDialog()
                    onCloseClick()
                },
                onConfirmClick = dismissCreateImageStopDialog,
            )
        }
        if (uiState.isServerErrorDialogVisible) {
            ServerErrorDialog(
                onRetryClick = {
                    dismissServerErrorDialog()
                    createProfileImage()
                },
            )
        }
        if (uiState.isNetworkErrorDialogVisible) {
            NetworkErrorDialog(
                onRetryClick = {
                    dismissNetworkErrorDialog()
                    createProfileImage()
                },
            )
        }
        CreateImageTopAppBar(onCloseClick = openCreateImageStopDialog)
        CreateImageContent(
            creatingImageWaitText = uiState.creatingImageWaitText.asString(),
            onNavigateToCreateImageComplete = onNavigateToCreateImageComplete,
        )
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
    creatingImageWaitText: String,
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
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(20.dp))
        val annotatedString = buildAnnotatedString {
            val splitIndex = creatingImageWaitText.indexOf("30초")
            if (splitIndex != -1) {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inverseOnSurface)) {
                    append(creatingImageWaitText.substring(0, splitIndex))
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primaryContainer)) {
                    append(creatingImageWaitText.substring(splitIndex, splitIndex + 3))
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inverseOnSurface)) {
                    append(creatingImageWaitText.substring(splitIndex + 3))
                }
            } else {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inverseOnSurface)) {
                    append(creatingImageWaitText)
                }
            }
        }
        Text(
            text = annotatedString,
            style = Subtitle2,
            textAlign = TextAlign.Center,
        )
        LoadingImage(
            resId = R.drawable.anim_loading,
            contentDescription = "Loading image",
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.inverseSurface)
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
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.inverseOnSurface)) {
                            append(stringResource(R.string.creating_image_exit_warning_prefix))
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.error)) {
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

@Composable
private fun CreateImageStopDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    ILabDialog(
        titleResId = R.string.creating_image_stop_confirmation,
        iconResId = null,
        iconDescription = null,
        firstDescriptionResId = R.string.creating_image_stop_warning_description1,
        secondDescriptionResId = R.string.creating_image_stop_warning_description2,
        cancelTextResId = R.string.creating_image_stop_confirm,
        confirmTextResId = R.string.creating_image_continue,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
    )
}

@DevicePreview
@Composable
fun CreateImageScreenPreview() {
    ILabTheme {
        CreateImageScreen(
            uiState = CreateImageState(
                creatingImageWaitText = UiText.StringResource(R.string.creating_image_wait_part1_description),
            ),
            onCloseClick = {},
            onNavigateToCreateImageComplete = {},
            openCreateImageStopDialog = {},
            dismissCreateImageStopDialog = {},
            createProfileImage = {},
            dismissServerErrorDialog = {},
            dismissNetworkErrorDialog = {},
        )
    }
}
