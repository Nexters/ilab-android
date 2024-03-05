package com.nexters.ilab.core.ui.component

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.nexters.ilab.android.core.designsystem.R
import com.nexters.ilab.android.core.designsystem.theme.Contents2
import com.nexters.ilab.core.ui.ComponentPreview

@Composable
fun ExampleImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Example Image Icon",
            modifier = Modifier
                .width(103.dp)
                .height(139.dp),
        )
    } else {
        Image(
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = modifier,
        )
    }
}

@Composable
fun NetworkImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = LocalContext.current

    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Network Image Icon",
            modifier = Modifier
                .width(186.dp)
                .aspectRatio(1f),
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}

@Composable
fun BackgroundImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (LocalInspectionMode.current) {
        Box(modifier = Modifier.fillMaxSize())
    } else {
        Image(
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}

@Composable
fun StyleImage(
    imageUrl: String,
    styleName: String,
    contentDescription: String,
    isSelectedIndex: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
) {
    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Style Image",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .aspectRatio(1f),
        )
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                NetworkImage(
                    imageUrl = imageUrl,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = modifier,
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(backgroundColor),
                ) {
                    if (isSelectedIndex) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                            contentDescription = "Check Icon",
                            modifier = Modifier.align(Alignment.Center),
                            tint = Color.Unspecified,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = styleName,
                style = Contents2,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

// https://coil-kt.github.io/coil/gifs/
@Composable
fun LoadingImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Loading Image Icon",
            modifier = Modifier
                .width(180.dp)
                .aspectRatio(1f),
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(resId)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = modifier,
            imageLoader = imageLoader,
        )
    }
}

@ComponentPreview
@Composable
fun ExampleImagePreview() {
    ExampleImage(
        resId = 0,
        contentDescription = "Example Image Icon",
    )
}

@ComponentPreview
@Composable
fun NetworkImagePreview() {
    NetworkImage(
        imageUrl = "",
        contentDescription = "Network Image Icon",
    )
}

@ComponentPreview
@Composable
fun BackgroundImagePreview() {
    BackgroundImage(
        resId = 0,
        contentDescription = "Loading Image Icon",
    )
}

@ComponentPreview
@Composable
fun LoadingImagePreview() {
    LoadingImage(
        resId = 0,
        contentDescription = "Background Image Icon",
    )
}

@ComponentPreview
@Composable
fun UnSelectedStyleImagePreview() {
    StyleImage(
        imageUrl = "",
        styleName = "#스타일",
        contentDescription = "Style Image",
        isSelectedIndex = false,
    )
}

@ComponentPreview
@Composable
fun SelectedStyleImagePreview() {
    StyleImage(
        imageUrl = "",
        styleName = "#스타일",
        contentDescription = "Style Image",
        isSelectedIndex = true,
    )
}
