package com.dima6120.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.dima6120.ui.theme.Yamalc
import com.dima6120.ui.theme.YamalcColors

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    tint: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            tint = tint,
            contentDescription = null
        )
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContentColor = YamalcColors.Gray78,
            disabledBackgroundColor = Color.Transparent
        ),
        elevation = elevation,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = Yamalc.type.title2
        )
    }
}