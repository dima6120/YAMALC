package com.dima6120.core_api.model

import com.dima6120.core_api.error.YamalcError

sealed class UseCaseResult<out T> {

    data class Success<T>(val value: T): UseCaseResult<T>()

    data class Error(val error: YamalcError): UseCaseResult<Nothing>()

    companion object {
        fun <T> success(value: T): Success<T> = Success(value)

        fun error(error: YamalcError): Error = Error(error)
    }
}

fun <T> UseCaseResult<T>.getYamalcErrorOrNull(): YamalcError? = (this as? UseCaseResult.Error)?.error