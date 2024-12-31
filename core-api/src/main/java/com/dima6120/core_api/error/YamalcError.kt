package com.dima6120.core_api.error

import kotlin.Error

sealed class YamalcError(cause: Throwable): Error(cause) {
    class Network(cause: Throwable): YamalcError(cause)
    class Unauthorized(cause: Throwable): YamalcError(cause)
    class Forbidden(cause: Throwable): YamalcError(cause)
    class NotFound(cause: Throwable): YamalcError(cause)
    class BadRequest(cause: Throwable): YamalcError(cause)
    class Unknown(cause: Throwable): YamalcError(cause)
}