package com.kbeanie.securityandroid

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context){
    val prefs: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun saveCredentials(username: String, password: String, token: String){
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_PASSWORD, password)
            .putString(KEY_TOKEN, token)
            .apply()
    }

    companion object {

        val FILE_NAME = "preferences"

        val KEY_USERNAME = "key_username"
        val KEY_PASSWORD = "key_password"
        val KEY_TOKEN = "key_token"
    }
}