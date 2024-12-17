package com.example.abakada.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.abakada.R

@SuppressLint("StaticFieldLeak")
object LoadingOverlayUtils {
    private var loadingOverlay: View? = null

    fun showLoadingOverlay(context: Any) { // Accept Any for Activity or Fragment
        if (loadingOverlay == null) {
            loadingOverlay = when (context) {
                is Activity -> context.layoutInflater.inflate(R.layout.loading_overlay, null)
                is Fragment -> context.layoutInflater.inflate(R.layout.loading_overlay, null)
                else -> throw IllegalArgumentException("Context must be an Activity or Fragment")
            }
        }

        val rootView: ViewGroup = when (context) {
            is Activity -> context.findViewById(android.R.id.content)
            is Fragment -> context.requireActivity().findViewById(android.R.id.content)
            else -> throw IllegalArgumentException("Context must be an Activity or Fragment")
        }
        rootView.addView(loadingOverlay)
    }

    fun hideLoadingOverlay(context: Any) { // Accept Any for Activity or Fragment
        val rootView: ViewGroup = when (context) {
            is Activity -> context.findViewById(android.R.id.content)
            is Fragment -> context.requireActivity().findViewById(android.R.id.content)
            else -> throw IllegalArgumentException("Context must be an Activity or Fragment")
        }
        rootView.removeView(loadingOverlay)
        loadingOverlay = null
    }
}