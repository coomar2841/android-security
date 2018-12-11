package com.kbeanie.securityandroid

import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class CredentialsActivity : AppCompatActivity() {

    private val TAG = CredentialsActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.credentials_activity)

        generateAssymetricKey()
    }

    private fun generateAssymetricKey() {
        SymmetricEncryptor().generateSymmetricKey("MySecuredApp")

//        var key = SymmetricEncryptor().getAsymmetricKey("MySecuredApp")
//        Log.i(TAG, "Key retrieved: " + key.toString())

        val encrypted = SymmetricEncryptor().encryptDataAsymmetric("MySecuredApp", "Kumar")
        Log.i(TAG, "Encrypted: ${encrypted}, Actual: Kumar")
        val decrypted = SymmetricEncryptor().decryptDataAsymmetric("MySecuredApp", encrypted)
        Log.i(TAG, "Decrypted: ${decrypted}, Actual: ${encrypted}")
    }
}