package com.dima6120.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = YamalcColors.PrimaryColorLight,
    secondary = YamalcColors.SecondaryColorLight,
    surface = YamalcColors.SurfaceColorLight,
    background = YamalcColors.BackgroundColorLight,
)

@Composable
fun YAMALCTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = Typography,
        content = content
    )
}