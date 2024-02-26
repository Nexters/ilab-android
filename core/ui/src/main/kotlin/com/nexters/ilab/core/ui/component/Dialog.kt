package com.nexters.ilab.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents2
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.core.ui.ComponentPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ILabDialog(
    titleResId: Int,
    iconResId: Int?,
    iconDescription: String?,
    firstDescriptionResId: Int,
    secondDescriptionResId: Int?,
    cancelTextResId: Int?,
    confirmTextResId: Int,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onCancelClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = titleResId),
                style = Title2,
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (iconResId != null && iconDescription != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(iconResId),
                    contentDescription = iconDescription,
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = firstDescriptionResId),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                style = Contents2,
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (secondDescriptionResId != null) {
                Text(
                    text = stringResource(id = secondDescriptionResId),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = Contents2,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                if (cancelTextResId != null) {
                    ILabButton(
                        onClick = onCancelClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(end = 4.dp),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        text = {
                            Text(
                                text = stringResource(id = cancelTextResId),
                                style = Subtitle2,
                            )
                        },
                    )
                }
                ILabButton(
                    onClick = onConfirmClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(start = 4.dp),
                    text = {
                        Text(
                            text = stringResource(id = confirmTextResId),
                            style = Subtitle2,
                        )
                    },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ServerErrorDialog(
    onRetryClick: () -> Unit,
) {
    ILabDialog(
        titleResId = R.string.server_error_title,
        iconResId = R.drawable.ic_server_error,
        iconDescription = "Network Error Icon",
        firstDescriptionResId = R.string.server_error_description1,
        secondDescriptionResId = R.string.server_error_description2,
        confirmTextResId = R.string.retry,
        cancelTextResId = null,
        onCancelClick = {},
        onConfirmClick = onRetryClick,
    )
}

@Composable
fun NetworkErrorDialog(
    onRetryClick: () -> Unit,
) {
    ILabDialog(
        titleResId = R.string.network_error_title,
        iconResId = R.drawable.ic_network_error,
        iconDescription = "Network Error Icon",
        firstDescriptionResId = R.string.network_error_description1,
        secondDescriptionResId = R.string.network_error_description2,
        confirmTextResId = R.string.retry,
        cancelTextResId = null,
        onCancelClick = {},
        onConfirmClick = onRetryClick,
    )
}

@ComponentPreview
@Composable
fun CreateImageStopDialogPreview() {
    ILabDialog(
        titleResId = R.string.creating_image_stop_confirmation,
        iconResId = null,
        iconDescription = null,
        firstDescriptionResId = R.string.creating_image_stop_warning_description1,
        secondDescriptionResId = R.string.creating_image_stop_warning_description2,
        cancelTextResId = R.string.creating_image_stop_confirm,
        confirmTextResId = R.string.creating_image_continue,
        onCancelClick = {},
        onConfirmClick = {},
    )
}

@ComponentPreview
@Composable
fun DeleteAccountDialogPreview() {
    ILabDialog(
        titleResId = R.string.setting_delete_account,
        iconResId = R.drawable.ic_warning,
        iconDescription = "Warning Icon",
        firstDescriptionResId = R.string.setting_delete_account_description,
        secondDescriptionResId = null,
        cancelTextResId = R.string.setting_delete_account_cancel,
        confirmTextResId = R.string.setting_delete_account_confirm,
        onCancelClick = {},
        onConfirmClick = {},
    )
}

@ComponentPreview
@Composable
fun ServerErrorDialogPreview() {
    ILabDialog(
        titleResId = R.string.server_error_title,
        iconResId = R.drawable.ic_server_error,
        iconDescription = "Network Error Icon",
        firstDescriptionResId = R.string.server_error_description1,
        secondDescriptionResId = R.string.server_error_description2,
        confirmTextResId = R.string.retry,
        cancelTextResId = null,
        onCancelClick = {},
        onConfirmClick = {},
    )
}

@ComponentPreview
@Composable
fun NetworkErrorDialogPreview() {
    ILabDialog(
        titleResId = R.string.network_error_title,
        iconResId = R.drawable.ic_network_error,
        iconDescription = "Network Error Icon",
        firstDescriptionResId = R.string.network_error_description1,
        secondDescriptionResId = R.string.network_error_description2,
        confirmTextResId = R.string.retry,
        cancelTextResId = null,
        onCancelClick = {},
        onConfirmClick = {},
    )
}
