package com.waiting.mycalendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet

/**
 * Created by hechao on 2018/1/27.
 */

class DayTextView : android.support.v7.widget.AppCompatTextView {

    var isToday = false
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        mPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isToday) {
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
            canvas.drawCircle(0f, 0f, (width / 2).toFloat(), mPaint)
        }
    }
}
