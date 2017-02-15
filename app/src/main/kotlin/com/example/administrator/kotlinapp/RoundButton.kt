package com.scrollerapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.Button
import com.example.administrator.kotlinapp.R

/**
 * Created by chentao on 2017/2/14.
 */
class RoundButton : Button {
    var viewType = 0
    val TYPE_CIRCLE = 1
    val TYPE_ROUND = 2
    val TYPE_OVAL = 3
    var viewSize = 0
    var radius = 0
    var mPaint : Paint? = null
    var nomalColor = Color.TRANSPARENT
    var selectColor = Color.TRANSPARENT
    var enabledColor = Color.TRANSPARENT
    var disabledColor = Color.TRANSPARENT
    var pressedColor = Color.TRANSPARENT
    var nomalTextColor = Color.BLACK
    var selectTextColor = Color.BLACK
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defstyle : Int) : super(context, attributeSet, defstyle) {
        init(context, attributeSet!!, defstyle)
    }

    @SuppressLint("NewApi")
    private fun  init(context: Context, attributeSet: AttributeSet? = null, defstyle: Int = 0) {
        val arr = context.obtainStyledAttributes(attributeSet, R.styleable.RoundButton,defstyle, 0)
        viewType = arr.getInt(R.styleable.RoundButton_type_view, TYPE_ROUND)
        nomalColor = arr.getColor(R.styleable.RoundButton_nomal_color, nomalColor)
        selectColor = arr.getColor(R.styleable.RoundButton_select_color, selectColor)
        nomalTextColor = arr.getColor(R.styleable.RoundButton_nomal_textcolor, nomalTextColor)
        selectTextColor = arr.getColor(R.styleable.RoundButton_select_textcolor, selectTextColor)
        enabledColor = arr.getColor(R.styleable.RoundButton_enabled_color, nomalColor)
        pressedColor = arr.getColor(R.styleable.RoundButton_pressed_color, nomalColor)
        disabledColor = arr.getColor(R.styleable.RoundButton_disabled_color, nomalColor)
        radius = arr.getDimensionPixelSize(R.styleable.RoundButton_viwe_radius, radius)
        background = null
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = nomalColor
        setTextColor(nomalTextColor)
    }

    override fun onDraw(canvas: Canvas?) {
        val rectf = RectF(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())
        if(viewType == TYPE_OVAL) {
            canvas!!.drawOval(rectf, mPaint)
        } else if(viewType == TYPE_CIRCLE) {
            canvas!!.drawCircle((viewSize / 2).toFloat(), (viewSize / 2).toFloat(), (viewSize / 2).toFloat(), mPaint)
        } else {
            canvas!!.drawRoundRect(rectf, radius.toFloat(), radius.toFloat(), mPaint)
        }
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if(viewType == TYPE_CIRCLE) {
            viewSize = Math.min(measuredHeight, measuredWidth)
        }
    }

    override fun setSelected(selected: Boolean) {
        if(selected) {
            setTextColor(selectTextColor)
            mPaint!!.color = selectColor
        } else {
            setTextColor(nomalTextColor)
            mPaint!!.color = nomalColor
        }
        invalidate()
        super.setSelected(selected)
    }

    override fun setEnabled(enabled: Boolean) {
        if (isEnabled) {
            mPaint!!.color = enabledColor
        } else {
            mPaint!!.color = disabledColor
        }
        invalidate()
        super.setEnabled(enabled)
    }

    override fun setPressed(pressed: Boolean) {
        if(pressed) {
            mPaint!!.color = pressedColor
        } else {
            if(isSelected) {
                mPaint!!.color = selectColor
            } else {
                mPaint!!.color = nomalColor
            }
        }
        invalidate()
        super.setPressed(pressed)
    }

}