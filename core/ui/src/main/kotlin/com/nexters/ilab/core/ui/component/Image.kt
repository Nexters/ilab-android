package com.nexters.ilab.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nexters.ilab.core.ui.ComponentPreview

@Composable
fun ExampleImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Example Image Icon",
            modifier = Modifier
                .width(103.dp)
                .height(139.dp),
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(resId)
                .crossfade(true)
                .build(),
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
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )
    }
}

@Composable
fun BackgroundImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    if (LocalInspectionMode.current) {
        Box(modifier = Modifier.fillMaxSize())
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(resId)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds,
            modifier = modifier,
        )
    }
}

@Composable
fun KeywordImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    if (LocalInspectionMode.current) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Keyword Image Icon",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .aspectRatio(1f),
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(resId)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = modifier,
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
        contentDescription = "Background Image Icon",
    )
}

@ComponentPreview
@Composable
fun KeywordImagePreview() {
    KeywordImage(
        resId = 0,
        contentDescription = "Keyword Image Icon",
    )
}
