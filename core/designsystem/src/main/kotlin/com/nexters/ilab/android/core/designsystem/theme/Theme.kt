package com.nexters.ilab.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorScheme = darkColorScheme(
    onPrimary = DarkBlue900,
    background = LightGray900,
    onBackground = DarkGray900,
    primaryContainer = DarkBlue600,
    onPrimaryContainer = White,
    inversePrimary = DarkBlue500,
    secondary = SystemGreen,
    onSecondary = White,
    secondaryContainer = LightBlue200,
    onSecondaryContainer = LightBlue900,
    onTertiary = DarkGray400,
    tertiaryContainer = DarkGray300,
    onTertiaryContainer = White,
    error = SystemRed,
    onError = White,
    errorContainer = SystemRed,
    onErrorContainer = White,
    surface = LightGray900,
    onSurface = White,
    inverseSurface = DarkGray100,
    inverseOnSurface = DarkGray500,
    outline = DarkGray200,
    scrim = Black,
)

private val LightColorScheme = lightColorScheme(
    onPrimary = LightBlue900,
    background = White,
    onBackground = LightGray900,
    primaryContainer = LightBlue600,
    onPrimaryContainer = White,
    inversePrimary = LightBlue500,
    secondary = SystemGreen,
    onSecondary = Black,
    secondaryContainer = LightBlue200,
    onSecondaryContainer = LightBlue900,
    onTertiary = LightGray400,
    tertiaryContainer = LightGray300,
    onTertiaryContainer = White,
    error = SystemRed,
    onError = White,
    errorContainer = SystemRed,
    onErrorContainer = White,
    surface = LightGray900,
    onSurface = Black,
    inverseSurface = LightGray100,
    inverseOnSurface = LightGray500,
    outline = LightGray200,
    scrim = Black,
)

@Composable
fun ILabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
