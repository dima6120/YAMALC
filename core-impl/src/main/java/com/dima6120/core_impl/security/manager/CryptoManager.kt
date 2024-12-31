package com.dima6120.core_impl.security.manager

interface CryptoManager {

    fun encrypt(plainText: String): String

    fun decrypt(encrypted: String): String
}