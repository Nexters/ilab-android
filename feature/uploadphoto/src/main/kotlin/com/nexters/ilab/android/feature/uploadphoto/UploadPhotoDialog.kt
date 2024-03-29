package com.nexters.ilab.android.feature.uploadphoto

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents1
import com.nexters.ilab.android.core.designsystem.theme.ILabTheme
import com.nexters.ilab.core.ui.ComponentPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPhotoDialog(
    onDismiss: () -> Unit,
    openPhotoPicker: () -> Unit,
    requestCameraPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onDismiss()
                                openPhotoPicker()
                            },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.photo_library),
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = Contents1,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onDismiss()
                                requestCameraPermission()
                            },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.take_photo),
                        modifier = Modifier.padding(vertical = 12.dp),
                        style = Contents1,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        },
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
    )
}

@ComponentPreview
@Composable
fun UploadPhotoDialogPreview() {
    ILabTheme {
        UploadPhotoDialog(
            onDismiss = {},
            openPhotoPicker = {},
            requestCameraPermission = {},
        )
    }
}
