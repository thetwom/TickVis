package de.moekadu.tickvis

import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.roundToLong

class TickVisualizer(context : Context, attrs : AttributeSet?, defStyleAttr: Int)
    : View(context, attrs, defStyleAttr) {

    var bpm = 60f
    val animator = TimeAnimator().apply {
        setTimeListener { animation, totalTime, deltaTime ->
            if (totalTime > next) {
                current = next
                next += (60f / bpm * 1000).roundToLong()
                odd = !odd
            }
            //paint.alpha = 255 - (255 * (totalTime - current).toFloat() / (next - current).toFloat()).toInt()
            Log.v("TickVis", "alpha = ${paint.alpha} tot=$totalTime, current=$current next=$next")
            invalidate()
        }
    }

    private val paint = Paint().apply {
        color = Color.RED
    }

    var odd = false
    var current = 0L
    var next = 0L
    var isRunning = false
        private set

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)

    fun start() {
        next = 0
        animator.start()
        isRunning = true
    }

    fun stop() {
        animator.end()
        isRunning = false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (odd)
            canvas?.drawRect(0f, 0f, 0.5f * width.toFloat(), height.toFloat(), paint)
        else
            canvas?.drawRect(0.5f*width.toFloat(), 0f, width.toFloat(), height.toFloat(), paint)
    }
}