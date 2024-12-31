package com.dima6120.core_impl.security.manager

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class CryptoManagerImpl @Inject constructor(): CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private val encryptCipher by lazy {
        Cipher.getInstance(TRANSFORMATION).apply { init(Cipher.ENCRYPT_MODE, getKey()) }
    }

    override fun encrypt(plainText: String): String {
        val encryptedBytes = encryptCipher.doFinal(plainText.toByteArray())
        val encryptedStringBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
        val ivStringBase64 = Base64.encodeToString(encryptCipher.iv, Base64.NO_WRAP)

        return "${encryptedStringBase64}.${ivStringBase64}"
    }

    override fun decrypt(encrypted: String): String {
        val array = encrypted.split(".")
        val cipherData = Base64.decode(array.first(), Base64.NO_WRAP)
        val iv = Base64.decode(array[1], Base64.NO_WRAP)
        val decryptedBytes = getDecryptCipherForIv(iv).doFinal(cipherData)

        return String(decryptedBytes)
    }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    companion object {
        private const val KEY_ALIAS = "yamalc_key"
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}