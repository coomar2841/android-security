package com.kbeanie.securityandroid

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.lang.Exception
import java.nio.charset.Charset
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class SymmetricEncryptor {
    val keystore: KeyStore = initializeKeystore()

    fun initializeKeystore(): KeyStore {
        // There could be many keystore providers.
        // We are interested in AndroidKeyStore
        val ks = KeyStore.getInstance("AndroidKeyStore")
        ks.load(null)
        return ks
    }

    fun generateSymmetricKey(alias: String) {
        // Specify the algorithm to be used
        val generator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        // Configurations for the key
        val generatorSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)

        generator.init(generatorSpec.build())
        // Generate the key
        generator.generateKey()
    }

    // Retrieve the reference to the key by passing the same alias
    // that was used while creating
    fun getAsymmetricKey(alias: String): SecretKey? {
        try {
            return keystore.getKey(alias, CharArray(0)) as SecretKey
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun encryptDataAsymmetric(alias: String, data: String): String {
        var key = getAsymmetricKey(alias)
        var plainTextByteArray = data.toByteArray(Charset.defaultCharset())

        var cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        var cipherText = cipher.doFinal(plainTextByteArray)

        // IV needs to be preserved which will be used during decryption
        // Encode cipher text and the iv to Base64 format
        // Concatenate both strings separated by a comma(,)
        return Base64.getEncoder()
            .encodeToString(cipherText) +
                "," +
                Base64.getEncoder().encodeToString(cipher.iv)
    }

    fun decryptDataAsymmetric(alias: String, data: String): String {
        var key = getAsymmetricKey(alias)

        // Extract the cipher text and the IV
        var parts = data.split(",")

        // Base64 decode of cipher text
        var plainTextByteArray = Base64.getDecoder().decode(parts[0])

        // Base64 decode of the IV
        var iv = Base64.getDecoder().decode(parts[1])

        var cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        var cipherText = cipher.doFinal(plainTextByteArray)

        return cipherText.toString(Charset.defaultCharset())
    }
}