package com.ytblocker

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ytblocker.admin.YTBlockerAdminReceiver
import com.ytblocker.data.PasswordManager
import com.ytblocker.service.YTBlockerService

class SetupActivity : AppCompatActivity() {

    private lateinit var passwordManager: PasswordManager
    private lateinit var dpm: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        passwordManager = PasswordManager(this)
        dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, YTBlockerAdminReceiver::class.java)

        setupAccessibilityButton()
        setupAdminButton()
        setupPasswordSection()
        setupCompleteButton()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val isAccessibilityEnabled = isAccessibilityServiceEnabled(this, YTBlockerService::class.java)
        val isAdminActive = dpm.isAdminActive(adminComponent)
        val hasPassword = passwordManager.hasPassword()

        // Step 1
        findViewById<TextView>(R.id.step1Status).apply {
            if (isAccessibilityEnabled) {
                text = "✓ Enabled"
                setTextColor(resources.getColor(R.color.green, theme))
            } else {
                text = "⚠ Not enabled"
                setTextColor(resources.getColor(R.color.red, theme))
            }
        }
        findViewById<Button>(R.id.btnEnableAccessibility).isEnabled = !isAccessibilityEnabled

        // Step 2
        findViewById<TextView>(R.id.step2Status).apply {
            if (isAdminActive) {
                text = "✓ Enabled"
                setTextColor(resources.getColor(R.color.green, theme))
            } else {
                text = "⚠ Not enabled"
                setTextColor(resources.getColor(R.color.red, theme))
            }
        }
        findViewById<Button>(R.id.btnEnableAdmin).isEnabled = !isAdminActive

        // Step 3
        findViewById<TextView>(R.id.step3Status).apply {
            if (hasPassword) {
                text = "✓ Password set"
                setTextColor(resources.getColor(R.color.green, theme))
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
        findViewById<Button>(R.id.btnSetPassword).isEnabled = !hasPassword
        findViewById<EditText>(R.id.editPassword).isEnabled = !hasPassword
        findViewById<EditText>(R.id.editPasswordConfirm).isEnabled = !hasPassword

        // Complete Button
        val btnComplete = findViewById<Button>(R.id.btnComplete)
        if (isAccessibilityEnabled && isAdminActive && hasPassword) {
            btnComplete.isEnabled = true
            btnComplete.alpha = 1.0f
        } else {
            btnComplete.isEnabled = false
            btnComplete.alpha = 0.4f
        }
    }

    private fun setupAccessibilityButton() {
        findViewById<Button>(R.id.btnEnableAccessibility).setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }
    }

    private fun setupAdminButton() {
        findViewById<Button>(R.id.btnEnableAdmin).setOnClickListener {
            if (!dpm.isAdminActive(adminComponent)) {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "This prevents you from uninstalling the content blocker app.")
                startActivity(intent)
            }
        }
    }

    private fun setupPasswordSection() {
        val editPass = findViewById<EditText>(R.id.editPassword)
        val editConfirm = findViewById<EditText>(R.id.editPasswordConfirm)

        findViewById<Button>(R.id.btnSetPassword).setOnClickListener {
            val pass = editPass.text.toString()
            val confirm = editConfirm.text.toString()

            if (pass.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            passwordManager.setPassword(pass)
            Toast.makeText(this, "Password saved!", Toast.LENGTH_SHORT).show()
            updateUI()
        }
    }

    private fun setupCompleteButton() {
        findViewById<Button>(R.id.btnComplete).setOnClickListener {
            // Hide launcher icon
            val componentName = ComponentName(this, SetupActivity::class.java)
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            
            Toast.makeText(this, "Setup complete! App icon hidden.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun isAccessibilityServiceEnabled(context: Context, accessibilityService: Class<*>): Boolean {
        var accessibilityEnabled = 0
        val service = context.packageName + "/" + accessibilityService.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                context.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            // Ignore
        }
        val textString = TextUtils.SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                textString.setString(settingValue)
                while (textString.hasNext()) {
                    val accessibilityServiceStr = textString.next()
                    if (accessibilityServiceStr.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
