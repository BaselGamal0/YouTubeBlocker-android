package com.ytblocker.data

import android.content.Context
import android.content.SharedPreferences
import java.security.MessageDigest

class PasswordManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ytblocker_prefs", Context.MODE_PRIVATE)

    fun hasPassword(): Boolean {
        return prefs.getString("password_hash", null) != null
    }

    fun setPassword(password: String) {
        val hash = hashPassword(password)
        prefs.edit().putString("password_hash", hash).apply()
    }

    fun verifyPassword(password: String): Boolean {
        val storedHash = prefs.getString("password_hash", null) ?: return false
        return hashPassword(password) == storedHash
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
