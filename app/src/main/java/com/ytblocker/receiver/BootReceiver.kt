package com.ytblocker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == "android.intent.action.QUICKBOOT_POWERON" || 
            intent.action == "com.vivo.intent.action.BOOT_COMPLETED") {
            
            // The accessibility service is automatically restarted by the Android system
            // We just need the receiver to be triggered to wake up the app process
            Toast.makeText(context, "YTBlocker started", Toast.LENGTH_SHORT).show()
        }
    }
}
