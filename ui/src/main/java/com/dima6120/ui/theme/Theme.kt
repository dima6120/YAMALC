package com.dima6120.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.dima6120.ui.Dimensions
import com.dima6120.ui.LocalDimensionsProvider
import com.dima6120.ui.Padding
import com.dima6120.ui.R
import com.dima6120.ui.Space

private val LightColors = lightColors(
    primary = YamalcColors.PrimaryColorLight,
    secondary = YamalcColors.SecondaryColorLight,
    surface = YamalcColors.SurfaceColorLight,
    background = YamalcColors.BackgroundColorLight,
)

private val Dimensions = Dimensions(
    padding = Padding(
        xs = 2.dp,
        s = 4.dp,
        m = 8.dp,
        l = 12.dp,
        xl = 16.dp,
        xxl = 20.dp,
        xxxl = 24.dp
    ),
    space = Space(
        xs = 2.dp,
        s = 4.dp,
        m = 8.dp,
        l = 12.dp,
        xl = 16.dp,
        xxl = 20.dp,
        xxxl = 24.dp
    )
)

object YamalcDimensions {

    val padding: Padding
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensionsProvider.current.padding

    val space: Space
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensionsProvider.current.space
}


@Composable
fun YAMALCTheme(content: @Composable () -> Unit) {

    CompositionLocalProvider(
        LocalDimensionsProvider provides Dimensions
    ) {
        MaterialTheme(
            colors = LightColors,
            typography = Typography,
            content = content
        )
    }

}