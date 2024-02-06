package com.nexters.ilab.android.feature.uploadphoto

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexters.ilab.android.core.common.extension.toUri
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.Contents2
import com.nexters.ilab.android.core.designsystem.theme.Gray500
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue200
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue900
import com.nexters.ilab.android.core.designsystem.theme.Subtitle1
import com.nexters.ilab.android.core.designsystem.theme.Title1
import com.nexters.ilab.core.ui.DevicePreview
import com.nexters.ilab.core.ui.component.ILabButton
import com.nexters.ilab.core.ui.component.ILabTopAppBar
import com.nexters.ilab.core.ui.component.NetworkImage
import com.nexters.ilab.core.ui.component.TopAppBarNavigationType

@Composable
internal fun UploadCheckRoute(
    onBackClick: () -> Unit,
    onNavigateToInputKeyword: () -> Unit,
    viewModel: UploadPhotoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { viewModel.setSelectedImageUri(it.toString()) }
        },
    )

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(isGranted = isGranted)
        },
    )

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val photoUri = it.toUri(context)
            viewModel.setSelectedImageUri(photoUri.toString())
        }
    }

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
                    cameraLauncher.launch(null)
                }

                is UploadPhotoSideEffect.UploadPhotoSuccess -> {}
            }
        }
    }

    UploadCheckScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        toggleUploadPhotoDialog = viewModel::toggleUploadPhotoDialog,
        openPhotoPicker = viewModel::openPhotoPicker,
        requestCameraPermission = viewModel::requestCameraPermission,
        onNavigateToInputKeyword = onNavigateToInputKeyword,
    )
}

@Composable
private fun UploadCheckScreen(
    uiState: UploadPhotoState,
    onBackClick: () -> Unit,
    toggleUploadPhotoDialog: (Boolean) -> Unit,
    openPhotoPicker: () -> Unit,
    requestCameraPermission: () -> Unit,
    onNavigateToInputKeyword: () -> Unit,
) {
    if (uiState.isUploadPhotoDialogVisible) {
        UploadPhotoDialog(
            onDismiss = { toggleUploadPhotoDialog(false) },
            openPhotoPicker = openPhotoPicker,
            requestCameraPermission = requestCameraPermission,
        )
    }

    Column {
        UploadCheckTopAppBar(onBackClick = onBackClick)
        UploadCheckContent(
            selectedPhotoUri = uiState.selectedPhotoUri,
            toggleUploadPhotoDialog = toggleUploadPhotoDialog,
            onNavigateToInputKeyword = onNavigateToInputKeyword
        )
    }
}

@Composable
private fun UploadCheckTopAppBar(
    onBackClick: () -> Unit,
) {
    ILabTopAppBar(
        titleRes = R.string.upload_check_top_title,
        navigationType = TopAppBarNavigationType.Back,
        navigationIconContentDescription = "navigation Icon",
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp),
        onNavigationClick = onBackClick,
    )
}

@Composable
private fun UploadCheckContent(
    selectedPhotoUri: String,
    toggleUploadPhotoDialog: (Boolean) -> Unit,
    onNavigateToInputKeyword: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.check_guide_twice),
            style = Title1,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.check_needed_for_accurate_result),
            style = Contents1,
            color = Gray500,
        )
        Spacer(modifier = Modifier.height(36.dp))
        NetworkImage(
            imageUrl = selectedPhotoUri,
            contentDescription = "upload image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 92.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
        )
        Spacer(modifier = Modifier.height(36.dp))
        GuideRow(
            resId = R.drawable.ic_guide_right,
            contentDescription = "guide right 1",
            text = stringResource(id = R.string.choice_good_example_first),
        )
        Spacer(modifier = Modifier.height(16.dp))
        GuideRow(
            resId = R.drawable.ic_guide_right,
            contentDescription = "guide right 2",
            text = stringResource(id = R.string.choice_good_example_second),
        )
        Spacer(modifier = Modifier.height(16.dp))
        GuideRow(
            resId = R.drawable.ic_guide_error,
            contentDescription = "guide error 1",
            text = stringResource(id = R.string.avoid_bad_example_first),
        )
        Spacer(modifier = Modifier.height(16.dp))
        GuideRow(
            resId = R.drawable.ic_guide_error,
            contentDescription = "guide error 2",
            text = stringResource(id = R.string.avoid_bad_example_second),
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 18.dp),
        ) {
            ILabButton(
                onClick = {
                    toggleUploadPhotoDialog(true)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 4.dp),
                containerColor = PurpleBlue200,
                contentColor = PurpleBlue900,
                text = {
                    Text(
                        text = stringResource(id = R.string.change_photo),
                        style = Subtitle1,
                    )
                },
            )
            ILabButton(
                onClick = onNavigateToInputKeyword,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 4.dp),
                text = {
                    Text(
                        text = stringResource(id = R.string.check),
                        style = Subtitle1,
                    )
                },
            )
        }
    }
}

@Composable
fun GuideRow(
    resId: Int,
    contentDescription: String,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = resId),
            contentDescription = contentDescription,
            // https://stackoverflow.com/questions/66211616/how-to-change-android-jetpack-compose-bottomappbar-icon-tint-color
            tint = Color.Unspecified,
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            style = Contents2,
            color = Color.Black,
        )
    }
}

@DevicePreview
@Composable
fun UploadCheckScreenPreview() {
    UploadCheckScreen(
        uiState = UploadPhotoState(
            selectedPhotoUri = "",
        ),
        onBackClick = {},
        toggleUploadPhotoDialog = {},
        openPhotoPicker = {},
        requestCameraPermission = {},
        onNavigateToInputKeyword = {},
    )
}
