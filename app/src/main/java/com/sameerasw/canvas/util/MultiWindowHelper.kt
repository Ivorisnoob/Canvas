package com.sameerasw.canvas.util

import android.app.Activity
import android.os.Build
import android.util.Log

/**
 * Helper for handling multi-window mode in the Notes app.
 * Multi-window support includes split-screen, picture-in-picture, and floating windows.
 */
object MultiWindowHelper {
    private const val TAG = "MultiWindowHelper"

    /**
     * Checks if the activity is currently in multi-window mode (split-screen, PiP, etc).
     * Useful for adjusting UI layout when in constrained spaces.
     */
    fun isInMultiWindowMode(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return false
        }
        return try {
            activity.isInMultiWindowMode
        } catch (e: Exception) {
            Log.e(TAG, "Error checking multi-window mode", e)
            false
        }
    }

    /**
     * Checks if the activity is in picture-in-picture mode.
     * Useful for adjusting UI when in PiP (typically a small floating window).
     */
    fun isInPictureInPictureMode(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return false
        }
        return try {
            activity.isInPictureInPictureMode
        } catch (e: Exception) {
            Log.e(TAG, "Error checking PiP mode", e)
            false
        }
    }

    /**
     * Enters picture-in-picture mode if supported.
     * Useful for note-taking in a floating window.
     */
    fun enterPictureInPictureMode(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Log.w(TAG, "PiP mode requires Android 8.0 (API 26)+")
            return false
        }

        return try {
            activity.enterPictureInPictureMode()
            Log.d(TAG, "Entered PiP mode")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error entering PiP mode", e)
            false
        }
    }

    /**
     * Gets the size classification of the current window for responsive UI.
     * Helps determine appropriate layout for small, medium, or large screens.
     */
    fun getWindowSizeClass(activity: Activity): WindowSizeClass {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            // Fallback for older APIs - return based on screen size
            val isLandscape = activity.resources.configuration.orientation ==
                    android.content.res.Configuration.ORIENTATION_LANDSCAPE
            return if (isLandscape) WindowSizeClass.EXPANDED else WindowSizeClass.COMPACT
        }

        return try {
            val windowMetrics = activity.windowManager.maximumWindowMetrics
            val bounds = windowMetrics.bounds
            val widthDp = bounds.width() / activity.resources.displayMetrics.density

            when {
                widthDp >= 840 -> WindowSizeClass.EXPANDED
                widthDp >= 600 -> WindowSizeClass.MEDIUM
                else -> WindowSizeClass.COMPACT
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error calculating window size class", e)
            WindowSizeClass.COMPACT
        }
    }

    enum class WindowSizeClass {
        COMPACT,   // Phone in portrait, narrow windows
        MEDIUM,    // Tablet or phone landscape, split-screen
        EXPANDED   // Tablet in landscape, large screens
    }
}

