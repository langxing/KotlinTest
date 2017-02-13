package com.example.administrator.kotlinapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by Administrator on 2017/2/8.
 */
class RoundImageView : ImageView {
    private var size = 0
    private var mPaint : Paint ? = null
    constructor(context: Context) : super(context)

    init {
        initData()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    init {
        initData()
    }

    constructor(context: Context, attributeSet: AttributeSet, def : Int) : super(context, attributeSet, def)
    init {
        initData()
    }

    private fun initData() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = Color.GREEN
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 3.toFloat()



    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
       /* var bitmap : Bitmap ? = null
        if (drawable is BitmapDrawable) {
           bitmap = (drawable as BitmapDrawable).bitmap
        }
        var target = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        //创建和目标一样大小的画布
        var mCanvas = Canvas(target)*/
        var mPath = Path()
        mPath.moveTo(50.toFloat(), 150.toFloat())
        mPath.lineTo(150.toFloat(), 30.toFloat())
        mPath.lineTo(20.toFloat(), 250.toFloat())

        canvas!!.drawColor(Color.WHITE)
        canvas!!.drawPath(mPath, mPaint)
    }

}