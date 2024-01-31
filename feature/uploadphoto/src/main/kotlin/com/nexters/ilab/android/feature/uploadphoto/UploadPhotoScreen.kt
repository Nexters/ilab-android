package com.nexters.ilab.android.feature.uploadphoto

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.common.extension.findActivity
import com.nexters.ilab.android.core.common.extension.openAppSettings
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents2
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue200
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue900
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.SystemGreen
import com.nexters.ilab.android.core.designsystem.theme.SystemRed
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ExampleImage
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun UploadPhotoRoute(
    onBackClick: () -> Unit,
    onNavigateToUploadCheck: () -> Unit,
    viewModel: UploadPhotoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val activity = LocalContext.current.findActivity()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.setSelectImageUri(uri.toString()) },
    )

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(isGranted = isGranted)
        },
    )

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is UploadPhotoSideEffect.openPhotoPicker -> {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }

                is UploadPhotoSideEffect.requestCameraPermission -> {
                    cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                }

                is UploadPhotoSideEffect.startCamera -> {
                    // TODO 콜백을 통해 이미지를 받아와야 함
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    activity.startActivity(intent)
                }

                is UploadPhotoSideEffect.UploadPhotoSuccess -> onNavigateToUploadCheck()
            }
        }
    }

    UploadPhotoScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        openPhotoPicker = viewModel::openPhotoPicker,
        requestCameraPermission = viewModel::requestCameraPermission,
        dismissPermissionDialog = viewModel::dismissPermissionDialog,
    )
}

@Composable
internal fun UploadPhotoScreen(
    uiState: UploadPhotoState,
    onBackClick: () -> Unit,
    openPhotoPicker: () -> Unit,
    requestCameraPermission: () -> Unit,
    dismissPermissionDialog: () -> Unit,
) {
    val activity = LocalContext.current.findActivity()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isPermissionDialogVisible) {
            PermissionDialog(
                permissionTextProvider = CameraPermissionTextProvider(),
                isPermanentlyDeclined = !activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA),
                onDismiss = dismissPermissionDialog,
                onOkClick = {
                    dismissPermissionDialog()
                    requestCameraPermission()
                },
                onGoToAppSettingsClick = { activity.openAppSettings() },
            )
        }

        UploadPhotoTopAppBar(onBackClick = onBackClick)
        UploadPhotoContent(
            onPhotoPickerClick = openPhotoPicker,
            onCameraClick = requestCameraPermission,
        )
    }
}

@Composable
private fun UploadPhotoTopAppBar(
    onBackClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = R.string.upload_top_title,
        navigationType = TopAppBarNavigationType.Back,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        onNavigationClick = onBackClick,
    )
}

@Composable
private fun UploadPhotoContent(
    onPhotoPickerClick: () -> Unit,
    onCameraClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SystemGreen)) {
                    append(stringResource(id = R.string.good))
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.good_example))
                }
            },
            style = Title2,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.good_example_description),
            style = Contents2,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImageRow(images = goodExamples)
        Spacer(modifier = Modifier.height(42.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SystemRed)) {
                    append(stringResource(id = R.string.bad))
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.bad_example))
                }
            },
            style = Title2,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.bad_example_description),
            style = Contents2,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImageRow(images = badExamples)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 4.dp, end = 4.dp, bottom = 18.dp),
        ) {
            ILabButton(
                onClick = onPhotoPickerClick,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 4.dp),
                containerColor = PurpleBlue200,
                contentColor = PurpleBlue900,
                text = {
                    Text(
                        text = stringResource(id = R.string.photo_library),
                        style = Subtitle1,
                    )
                },
            )
            ILabButton(
                onClick = onCameraClick,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 4.dp),
                text = {
                    Text(
                        text = stringResource(id = R.string.take_photo),
                        style = Subtitle1,
                    )
                },
            )
        }
    }
}

val goodExamples = persistentListOf(
    Pair(R.drawable.ic_good_example1, "good example 1"),
    Pair(R.drawable.ic_good_example2, "good example 2"),
    Pair(R.drawable.ic_good_example3, "good example 3"),
)
val badExamples = persistentListOf(
    Pair(R.drawable.ic_bad_example1, "bad example 1"),
    Pair(R.drawable.ic_bad_example2, "bad example 2"),
    Pair(R.drawable.ic_bad_example3, "bad example 3"),
)

@Composable
fun ImageRow(images: ImmutableList<Pair<Int, String>>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        images.forEachIndexed { index, (resId, contentDescription) ->
            if (index > 0) Spacer(Modifier.width(12.dp))
            ExampleImage(
                resId = resId,
                contentDescription = contentDescription,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .weight(1f),
            )
        }
    }
}

@DevicePreview
@Composable
fun UploadPhotoScreenPreview() {
    UploadPhotoScreen(
        uiState = UploadPhotoState(),
        onBackClick = {},
        openPhotoPicker = {},
        requestCameraPermission = {},
        dismissPermissionDialog = {},
    )
}
