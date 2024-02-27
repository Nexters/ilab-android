package com.nexters.ilab.android.feature.uploadphoto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.common.extension.noRippleClickable
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.core.ui.ComponentPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissClick,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.permission_required),
                    style = Subtitle2,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = permissionTextProvider.getDescription(
                        isPermanentlyDeclined = isPermanentlyDeclined,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Text(
                    text = if (isPermanentlyDeclined) {
                        stringResource(id = R.string.go_to_app_setting)
                    } else {
                        stringResource(id = R.string.check)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .noRippleClickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onConfirmClick()
                            }
                        },
                    textAlign = TextAlign.Center,
                    style = Subtitle2,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.background),
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "카메라 권한을 거부하였습니다.\n앱 설정으로 이동하여 권한을 부여할 수 있습니다."
        } else {
            "프로필 사진을 만들기 위해서는 카메라 접근 권한이 필요합니다."
        }
    }
}

@ComponentPreview
@Composable
fun PermissionDialogPreview() {
    PermissionDialog(
        permissionTextProvider = CameraPermissionTextProvider(),
        isPermanentlyDeclined = false,
        onDismissClick = {},
        onConfirmClick = {},
        onGoToAppSettingsClick = {},
    )
}
