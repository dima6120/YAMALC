package com.dima6120.ui.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

sealed class TextUIModel {

    data class StringResource(@StringRes val id: Int, val args: List<Any> = emptyList()): TextUIModel()

    data class ClearText(val text: String): TextUIModel()

    companion object {

        fun stringResource(
            @StringRes id: Int,
            vararg args: Any = emptyArray()
        ): StringResource = StringResource(id, args.toList())

        fun clearText(text: String): ClearText = ClearText(text)
    }
}

val TextUIModel.text: String
    @Composable
    @ReadOnlyComposable
    get() =
        when (this) {
            is TextUIModel.ClearText -> this.text
            is TextUIModel.StringResource -> {
                val args = this.args.map { arg ->
                    if (arg is TextUIModel) arg.text else arg
                }

                stringResource(id = this.id, *args.toTypedArray())
            }
        }

fun Any.toTextUIModel(@StringRes id: Int): TextUIModel = TextUIModel.stringResource(id, this)

fun Any.toTextUIModel(): TextUIModel = TextUIModel.clearText(this.toString())