package com.dima6120.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.dima6120.ui.R
import com.dima6120.ui.models.ErrorUIModel
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.Yamalc

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    error: TextUIModel,
    onButtonClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Yamalc.padding.xl)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(Yamalc.space.s),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error.text,
                style = Yamalc.type.title2
            )

            Button(
                onClick = onButtonClick
            ) {
                Text(text = stringResource(id = R.string.try_again_button))
            }
        }
    }
}