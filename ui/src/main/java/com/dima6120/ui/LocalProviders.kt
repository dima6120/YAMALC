package com.dima6120.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.dima6120.core_api.ApplicationComponentProvider

val LocalApplicationComponentProvider =
    staticCompositionLocalOf<ApplicationComponentProvider> { error("No ApplicationComponentProvider") }


@Immutable
data class Padding(
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
    val xxl: Dp,
    val xxxl: Dp
)

@Immutable
data class Space(
    val xs: Dp,
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
    val xxl: Dp,
    val xxxl: Dp
)

@Immutable
data class Dimensions(
    val padding: Padding,
    val space: Space
)

val LocalDimensionsProvider = staticCompositionLocalOf<Dimensions> { error("No Dimensions") }

@Immutable
data class YamalcTypography(
    val appBarTitle: TextStyle,

    val bottomItemTitle: TextStyle,

    val fieldTitle: TextStyle,
    val fieldValue: TextStyle,

    val title1: TextStyle,
    val title2: TextStyle,

    val body1: TextStyle,
)

val LocalYamalcTypographyProvider = staticCompositionLocalOf<YamalcTypography> { error("No Typography") }

