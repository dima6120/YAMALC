package com.dima6120.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dima6120.ui.R
import com.dima6120.ui.models.ErrorUIModel
import com.dima6120.ui.models.TextUIModel
import com.dima6120.ui.models.text
import com.dima6120.ui.theme.YAMALCTheme
import com.dima6120.ui.theme.Yamalc

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorUIModel: ErrorUIModel,
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
                text = errorUIModel.title.text,
                style = MaterialTheme.typography.h6
            )
            
            Text(
                textAlign = TextAlign.Center,
                text = errorUIModel.text.text
            )
            
            Button(
                onClick = onButtonClick
            ) {
                Text(text = errorUIModel.button.text)
            }
        }
    }
}

@Preview(widthDp = 300, heightDp = 300)
@Composable
private fun ErrorScreenPreview() {
    YAMALCTheme {
        ErrorScreen(
            errorUIModel = ErrorUIModel(
                title = TextUIModel.stringResource(R.string.network_error_title),
                text = TextUIModel.stringResource(R.string.network_error_text),
                button = TextUIModel.stringResource(R.string.try_again_button)
            )
        )
    }
}