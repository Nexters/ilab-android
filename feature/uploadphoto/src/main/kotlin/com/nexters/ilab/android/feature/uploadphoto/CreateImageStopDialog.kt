package com.nexters.ilab.android.feature.uploadphoto

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents2
import com.nexters.ilab.android.core.designsystem.theme.Gray500
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue200
import com.nexters.ilab.android.core.designsystem.theme.PurpleBlue900
import com.nexters.ilab.android.core.designsystem.theme.Subtitle2
import com.nexters.ilab.android.core.designsystem.theme.Title2
import com.nexters.ilab.core.ui.ComponentPreview
import com.nexters.ilab.core.ui.component.ILabButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateImageStopDialog(
    onContinueClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onContinueClick,
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
                text = stringResource(id = R.string.creating_image_stop_confirmation),
                style = Title2,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.creating_image_stop_warning_description1),
                color = Gray500,
                style = Contents2,
            )
            Text(
                text = stringResource(id = R.string.creating_image_stop_warning_description2),
                color = Gray500,
                style = Contents2,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                ILabButton(
                    onClick = onConfirmClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(end = 4.dp),
                    containerColor = PurpleBlue200,
                    contentColor = PurpleBlue900,
                    text = {
                        Text(
                            text = stringResource(id = R.string.creating_image_stop_confirm),
                            style = Subtitle2,
                        )
                    },
                )
                ILabButton(
                    onClick = onContinueClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(start = 4.dp),
                    text = {
                        Text(
                            text = stringResource(id = R.string.creating_image_continue),
                            style = Subtitle2,
                        )
                    },
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@ComponentPreview
@Composable
fun CreateImageStopDialogPreview() {
    CreateImageStopDialog(
        onContinueClick = {},
        onConfirmClick = {},
    )
}
