package com.example.abakada.student

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.color
import com.example.abakada.R


class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
    }
    fun drawLine(color: Int) {
        paint.color = color
        invalidate() // Redraw the view with the new color
    }
    interface OnMatchListener {
        fun onMatch(startViewTag: Int, endViewTag: Int)
    }

    private var matchListener: OnMatchListener? = null

    fun setOnMatchListener(listener: OnMatchListener) {
        matchListener = listener
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(startX, startY, endX, endY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                endX = event.x
                endY = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                // Handle line drawing completion
                checkMatch()
            }
        }
        return true
    }
    private fun checkMatch() {
        val imageContainer = (parent as ViewGroup).findViewById<LinearLayout>(R.id.imagesContainer)
        val answerContainer = (parent as ViewGroup).findViewById<LinearLayout>(R.id.answersContainer)

        val startView = findViewAtPoint(startX, startY, imageContainer) as? ImageView
        val endView = findViewAtPoint(endX, endY, answerContainer) as? TextView

        if (startView != null && endView != null) {
            val startViewTag = startView.tag as Int
            val endViewTag = endView.tag as Int

            matchListener?.onMatch(startViewTag, endViewTag)
        }
    }

    private fun findViewAtPoint(x: Float, y: Float, container: ViewGroup): View? {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (x >= child.left && x <= child.right && y >= child.top && y <= child.bottom) {
                return child
            }
        }
        return null
    }
}