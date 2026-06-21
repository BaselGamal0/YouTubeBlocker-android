package com.ytblocker.data

import android.os.SystemClock

object SecurityManager {
    private var isSettingsUnlocked: Boolean = false
    private var unlockTime: Long = 0

    fun unlock() {
        isSettingsUnlocked = true
        unlockTime = SystemClock.elapsedRealtime()
    }

    fun lock() {
        isSettingsUnlocked = false
        unlockTime = 0
    }

    fun isUnlocked(): Boolean {
        if (!isSettingsUnlocked) return false
        // Automatically re-lock after 5 minutes
        if (SystemClock.elapsedRealtime() - unlockTime > 300000) {
            lock()
            return false
        }
        return true
    }
}
