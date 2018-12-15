package com.kbeanie.securityandroid

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context){
    val prefs: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun setKeyGenerated(value: Boolean){
        prefs.edit()
            .putBoolean(KEY_GENERATED, value)
            .apply()
    }

    fun isKeyGenerated(): Boolean{
        return prefs.getBoolean(KEY_GENERATED, false)
    }

    companion object {

        val FILE_NAME = "preferences"
        val KEY_GENERATED = "key_key_generated"
    }
}