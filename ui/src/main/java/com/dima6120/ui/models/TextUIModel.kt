package com.dima6120.ui.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

sealed class TextUIModel {

    data class StringResource(@StringRes val id: Int): TextUIModel()

    data class ClearText(val text: String): TextUIModel()

    companion object {

        fun stringResource(@StringRes id: Int): TextUIModel.StringResource = TextUIModel.StringResource(id)

        fun clearText(text: String): TextUIModel.ClearText = TextUIModel.ClearText(text)
    }
}



val TextUIModel.text: String
    @Composable
    @ReadOnlyComposable
    get() =
        when (this) {
            is TextUIModel.ClearText -> this.text
            is TextUIModel.StringResource -> stringResource(id = this.id)
        }