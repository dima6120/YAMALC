package com.dima6120.ui.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.Yamalc

@Composable
fun TopAppBarTitle(
    modifier: Modifier = Modifier,
    title: TextUIModel
) {
    Text(
        modifier = modifier,
        text = title.text,
        overflow = TextOverflow.Ellipsis,
        style = Yamalc.type.appBarTitle,
        maxLines = 1
    )
}