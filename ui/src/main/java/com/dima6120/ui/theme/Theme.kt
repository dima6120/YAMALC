package com.dima6120.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dima6120.ui.Dimensions
import com.dima6120.ui.LocalDimensionsProvider
import com.dima6120.ui.LocalYamalcTypographyProvider
import com.dima6120.ui.Padding
import com.dima6120.ui.Space
import com.dima6120.ui.YamalcTypography

private val LightColors = lightColors(
    primary = YamalcColors.PrimaryColorLight,
    primaryVariant = YamalcColors.PrimaryVariantColorLight,
    secondary = YamalcColors.SecondaryColorLight,
    secondaryVariant = YamalcColors.SecondaryVariantColorLight,
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

private val YamalcTypography = YamalcTypography(
    appBarTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bottomItemTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    fieldTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    fieldValue = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    title1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    title2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

object Yamalc {

    val padding: Padding
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensionsProvider.current.padding

    val space: Space
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensionsProvider.current.space

    val type: YamalcTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalYamalcTypographyProvider.current
}


@Composable
fun YAMALCTheme(content: @Composable () -> Unit) {

    CompositionLocalProvider(
        LocalDimensionsProvider provides Dimensions,
        LocalYamalcTypographyProvider provides YamalcTypography
    ) {
        MaterialTheme(
            colors = LightColors,
            typography = Typography,
            content = content
        )
    }

}