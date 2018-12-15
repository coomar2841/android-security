package com.kbeanie.securityandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.credentials_activity.*

class CredentialsActivity : AppCompatActivity() {

    private val TAG = CredentialsActivity::class.java.simpleName

    private lateinit var prefs: Prefs
    private var username = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.credentials_activity)

        prefs = Prefs(this)

        // Check if a secret key is generated already
        if (!prefs.isKeyGenerated()) {
            generateAssymetricKey()
            prefs.setKeyGenerated(true)
        }

        btnLogin.setOnClickListener {
            username = etUsername.text.toString()
            encrypt(username)
        }
        btnDecrypt.setOnClickListener {
            decrypt()
        }
    }

    // Generates a secret key in the keystore
    private fun generateAssymetricKey() {
        SymmetricCryptography().generateSymmetricKey(Constants.KEYSTORE_ALIAS)
    }

    private fun encrypt(username: String) {
        val encryptedUsername = SymmetricCryptography().encryptDataAsymmetric(Constants.KEYSTORE_ALIAS, username)
        tvUsername.setText(encryptedUsername)
    }

    private fun decrypt() {
        val username = SymmetricCryptography().decryptDataAsymmetric(Constants.KEYSTORE_ALIAS, username)
        tvUsername.setText(username)
    }
}