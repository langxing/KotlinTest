package com.example.administrator.kotlinapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by Administrator on 2017/2/14.
 */
class CustomTextView : TextView {
    var mPaint = Paint()
    constructor(context: Context, attributeSet: AttributeSet ? = null, defstyle : Int = 0) : super(context, attributeSet) {

        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        val rectf = RectF(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())
        canvas
        super.onDraw(canvas)

    }
}