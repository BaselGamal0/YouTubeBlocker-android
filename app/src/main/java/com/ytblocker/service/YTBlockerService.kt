package com.ytblocker.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView

class YTBlockerService : AccessibilityService() {

    private var windowManager: WindowManager? = null
    private var blockOverlay: android.view.View? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isOverlayShowing = false

    override fun onServiceConnected() {
        super.onServiceConnected()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createOverlay()
    }

    private fun createOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

        // Simple red overlay
        val textView = TextView(this).apply {
            text = "🚫 YOUTUBE BLOCKED"
            textSize = 32f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#FF1744"))
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }
        
        blockOverlay = textView
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: return
        val className = event.className?.toString() ?: ""

        // Detect YouTube App
        if (packageName == "com.google.android.youtube") {
            blockApp()
            return
        }

        // Detect Chrome navigating to YouTube
        if (packageName == "com.android.chrome") {
            val rootNode = rootInActiveWindow
            if (rootNode != null) {
                // Find URL bar node
                val urlNodes = rootNode.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar")
                if (urlNodes.isNotEmpty()) {
                    val url = urlNodes[0].text?.toString() ?: ""
                    if (url.contains("youtube.com")) {
                        blockApp()
                        return
                    }
                }
                
                // Fallback check for web content containing youtube URL
                // We check the content description of nodes, as WebViews sometimes expose the URL there
                // But text matching is more complex. The URL bar check is usually sufficient.
            }
        }
    }

    private fun blockApp() {
        if (isOverlayShowing) return

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
                windowManager?.addView(blockOverlay, blockOverlay?.layoutParams)
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
