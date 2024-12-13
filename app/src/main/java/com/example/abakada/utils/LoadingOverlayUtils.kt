package com.example.abakada.utils // Adjust the package name as needed

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.example.abakada.R // Adjust the package name as needed

@SuppressLint("StaticFieldLeak")
object LoadingOverlayUtils {
    private var loadingOverlay: View? = null

    fun showLoadingOverlay(activity: Activity) {
        if (loadingOverlay == null) {
            loadingOverlay = activity.layoutInflater.inflate(R.layout.loading_overlay, null)
        }

        val rootView: ViewGroup = activity.findViewById(android.R.id.content)
        rootView.addView(loadingOverlay)
    }

    fun hideLoadingOverlay(activity: Activity) {
        val rootView: ViewGroup = activity.findViewById(android.R.id.content)
        rootView.removeView(loadingOverlay)
        loadingOverlay = null
    }
}