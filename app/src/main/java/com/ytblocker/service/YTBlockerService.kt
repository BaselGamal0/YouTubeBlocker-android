package com.ytblocker.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import android.widget.Toast
import com.ytblocker.SetupActivity
import com.ytblocker.data.BlockedSites
import com.ytblocker.data.SecurityManager

class YTBlockerService : AccessibilityService() {

    private var windowManager: WindowManager? = null
    private var blockOverlay: TextView? = null
    private var overlayParams: WindowManager.LayoutParams? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isOverlayShowing = false

    // Browser packages to monitor for URL-based blocking
    private val browserPackages = setOf(
        "com.android.chrome",
        "org.mozilla.firefox",
        "com.opera.browser",
        "com.opera.mini.native",
        "com.brave.browser",
        "com.microsoft.emmx",           // Edge
        "com.vivaldi.browser",
        "com.duckduckgo.mobile.android",
        "com.sec.android.app.sbrowser", // Samsung Internet
        "com.UCMobile.intl",            // UC Browser
        "com.kiwibrowser.browser",
        "org.chromium.chrome",
        "com.mi.globalbrowser",         // Xiaomi Browser
        "com.huawei.browser",
        "mark.via.gp",                  // Via Browser
    )

    // URL bar view IDs for different browsers
    private val urlBarIds = listOf(
        "com.android.chrome:id/url_bar",
        "com.android.chrome:id/search_box_text",
        "org.mozilla.firefox:id/url_bar_title",
        "org.mozilla.firefox:id/mozac_browser_toolbar_url_view",
        "com.opera.browser:id/url_field",
        "com.brave.browser:id/url_bar",
        "com.microsoft.emmx:id/url_bar",
        "com.vivaldi.browser:id/url_bar",
        "com.sec.android.app.sbrowser:id/location_bar_edit_text",
        "com.duckduckgo.mobile.android:id/omnibarTextInput",
    )

    override fun onServiceConnected() {
        super.onServiceConnected()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createOverlay()
    }

    private fun createOverlay() {
        overlayParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        overlayParams?.gravity = Gravity.CENTER

        // Red overlay with dynamic text
        blockOverlay = TextView(this).apply {
            text = "🚫 BLOCKED"
            textSize = 32f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#FF1744"))
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: return
        val className = event.className?.toString() ?: ""

        // 1. Check if user is trying to access Settings to disable/uninstall
        if (packageName.contains("settings", ignoreCase = true)) {
            if (handleSettingsProtection(event, className, packageName)) {
                return
            }
        }

        // 2. Check if the app itself is in the blocked packages list
        val appCategory = BlockedSites.getBlockedPackageCategory(packageName)
        if (appCategory != null) {
            blockApp(appCategory)
            return
        }

        // 3. Check if a browser is navigating to a blocked website
        if (packageName in browserPackages) {
            checkBrowserUrl(packageName)
        }
    }

    /**
     * Checks if the user is attempting to access Settings screens to disable/uninstall our app,
     * and blocks access unless SecurityManager is unlocked.
     */
    private fun handleSettingsProtection(event: AccessibilityEvent, className: String, packageName: String): Boolean {
        // If the SecurityManager is unlocked, allow settings modification
        if (SecurityManager.isUnlocked()) {
            return false
        }

        // Define keywords that target settings screens for this app
        val isSettingsDetailScreen = className.contains("InstalledAppDetails", ignoreCase = true) ||
                className.contains("AppInfo", ignoreCase = true) ||
                className.contains("DeviceAdmin", ignoreCase = true) ||
                className.contains("ToggleAccessibility", ignoreCase = true) ||
                className.contains("ToggleFeature", ignoreCase = true) ||
                className.contains("AccessibilitySettings", ignoreCase = true)

        if (isSettingsDetailScreen) {
            val rootNode = rootInActiveWindow ?: return false
            if (containsAppKeywords(rootNode)) {
                blockSettingsAccess()
                return true
            }
        }

        // Double check fallback: generic check for any Settings window text
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val rootNode = rootInActiveWindow ?: return false
            if (containsAppKeywords(rootNode)) {
                // If it's a settings window, verify if it has buttons like Uninstall, Force Stop, Deactivate, Turn off
                if (hasSettingsActionKeywords(rootNode)) {
                    blockSettingsAccess()
                    return true
                }
            }
        }

        return false
    }

    private fun containsAppKeywords(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false

        val text = node.text?.toString() ?: ""
        if (text.contains("System Service", ignoreCase = true) || 
            text.contains("com.ytblocker", ignoreCase = true)) {
            return true
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (containsAppKeywords(child)) {
                return true
            }
        }
        return false
    }

    private fun hasSettingsActionKeywords(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false

        val text = node.text?.toString() ?: ""
        val actionKeywords = listOf(
            "uninstall", "force stop", "deactivate", "disable", "turn off", 
            "clear data", "clear storage", "use system service"
        )
        for (keyword in actionKeywords) {
            if (text.contains(keyword, ignoreCase = true)) {
                return true
            }
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (hasSettingsActionKeywords(child)) {
                return true
            }
        }
        return false
    }

    private fun blockSettingsAccess() {
        // Show warning toast
        Toast.makeText(
            this,
            "Access to System Service settings is locked. Enter password in the app first.",
            Toast.LENGTH_LONG
        ).show()

        // Kick the user out of Settings
        performGlobalAction(GLOBAL_ACTION_BACK)
        handler.postDelayed({
            performGlobalAction(GLOBAL_ACTION_HOME)
        }, 150)

        // Launch SetupActivity to let them unlock
        handler.postDelayed({
            try {
                val intent = Intent(this, SetupActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                startActivity(intent)
            } catch (e: Exception) {
                // Ignore
            }
        }, 300)
    }

    /**
     * Scans the browser's URL bar for blocked domains.
     */
    private fun checkBrowserUrl(browserPackage: String) {
        val rootNode = rootInActiveWindow ?: return

        // Try known URL bar IDs
        for (urlBarId in urlBarIds) {
            val urlNodes = rootNode.findAccessibilityNodeInfosByViewId(urlBarId)
            if (urlNodes.isNotEmpty()) {
                val url = urlNodes[0].text?.toString() ?: ""
                val category = BlockedSites.getBlockedCategory(url)
                if (category != null) {
                    blockApp(category)
                    return
                }
            }
        }

        // Generic fallback: search for any EditText that may contain a URL
        // Some browsers use custom URL bar IDs not in our list
        try {
            val allNodes = rootNode.findAccessibilityNodeInfosByViewId("$browserPackage:id/url_bar")
            if (allNodes.isNotEmpty()) {
                val url = allNodes[0].text?.toString() ?: ""
                val category = BlockedSites.getBlockedCategory(url)
                if (category != null) {
                    blockApp(category)
                    return
                }
            }
        } catch (_: Exception) {
            // Ignore if the ID doesn't exist
        }
    }

    private fun blockApp(category: String) {
        if (isOverlayShowing) return

        // Update overlay text with the password prompt
        blockOverlay?.text = "🔒 Enter Password"

        // 1. Perform Global Back Action to close the app
        performGlobalAction(GLOBAL_ACTION_BACK)

        // Try global home as fallback
        handler.postDelayed({
            performGlobalAction(GLOBAL_ACTION_HOME)
        }, 100)

        // 2. Show the overlay briefly
        showOverlay()

        // Hide overlay after a few seconds
        handler.postDelayed({
            hideOverlay()
        }, 3000)
    }

    private fun showOverlay() {
        if (!isOverlayShowing && blockOverlay != null) {
            try {
                windowManager?.addView(blockOverlay, overlayParams)
                isOverlayShowing = true
            } catch (e: Exception) {
                // View already added or other window manager error
            }
        }
    }

    private fun hideOverlay() {
        if (isOverlayShowing && blockOverlay != null) {
            try {
                windowManager?.removeView(blockOverlay)
            } catch (e: Exception) {
                // View not attached
            } finally {
                isOverlayShowing = false
            }
        }
    }

    override fun onInterrupt() {
        // Accessibility service interrupted
        hideOverlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideOverlay()
    }
}
