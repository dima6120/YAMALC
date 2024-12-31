package com.dima6120.core_impl.utils

object PkceGenerator {

    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '-' + '.' + '_' + '~'

    fun generateVerifier(length: Int): String =
        (1..length)
            .map { allowedChars.random() }
            .joinToString("")
}