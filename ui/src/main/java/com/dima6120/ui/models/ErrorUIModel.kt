package com.dima6120.ui.models

import com.dima6120.core_api.error.YamalcError
import com.dima6120.ui.R

data class ErrorUIModel(
    val title: TextUIModel,
    val text: TextUIModel,
    val button: TextUIModel
)

fun YamalcError.toTextUIModel(): TextUIModel {
    val id = when (this) {
        is YamalcError.Network -> R.string.network_error_title
        is YamalcError.BadRequest,
        is YamalcError.Forbidden,
        is YamalcError.NotFound,
        is YamalcError.Unauthorized,
        is YamalcError.Unknown -> R.string.unknown_error_title
    }

    return TextUIModel.stringResource(id)
}

fun YamalcError.toErrorUIModel(): ErrorUIModel {
    val title: TextUIModel
    val text: TextUIModel
    val button: TextUIModel

    when (this) {
        is YamalcError.Network -> {
            title = TextUIModel.stringResource(R.string.network_error_title)
            text = TextUIModel.stringResource(R.string.network_error_text)
            button = TextUIModel.stringResource(R.string.try_again_button)
        }
        is YamalcError.BadRequest,
        is YamalcError.Forbidden,
        is YamalcError.NotFound,
        is YamalcError.Unauthorized,
        is YamalcError.Unknown -> {
            title = TextUIModel.stringResource(R.string.unknown_error_title)
            text = TextUIModel.stringResource(R.string.unknown_error_text)
            button = TextUIModel.stringResource(R.string.try_again_button)
        }
    }

    return ErrorUIModel(
        title = title,
        text = text,
        button = button
    )
}
