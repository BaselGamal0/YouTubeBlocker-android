package com.ytblocker.receiver

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import com.ytblocker.SetupActivity

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == "android.intent.action.QUICKBOOT_POWERON" || 
            intent.action == "com.vivo.intent.action.BOOT_COMPLETED") {
            
            // The accessibility service is automatically restarted by the Android system
            // We just need the receiver to be triggered to wake up the app process
            Toast.makeText(context, "YTBlocker started", Toast.LENGTH_SHORT).show()

            // Ensure launcher icon is restored/enabled on boot
            try {
                val componentName = ComponentName(context, SetupActivity::class.java)
                context.packageManager.setComponentEnabledSetting(
                    componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )
            } catch (e: Exception) {
                // Ignore
            }
        }
    }
}
