package com.scrollerapplication

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.example.administrator.kotlinapp.R

/**
 * Created by chentao on 2017/2/13.
 */
class RoundImageView : ImageView {
    var viewWidth = 0
    var viewHeight = 0
    var srcBitmap: Bitmap ? = null
    var viewType = 0
    val TYPE_CIRCLE = 1
    val TYPE_ROUND = 2
    val TYPE_OVAL = 3
    var radious = 0
    var borderColor = 0
    var borderWidth = 0
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defstyle: Int) : super(context, attributeSet, defstyle) {
        init(context, attributeSet, defstyle)
    }

    fun updateByUrl(bitmap: Bitmap) {
        if(bitmap == null) return
        srcBitmap = bitmap
        invalidate()
    }

    private fun init(context: Context, attributeSet: AttributeSet? = null, defstyle: Int = 0) {
      var array: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RoundImageView, defstyle, 0)
        srcBitmap = BitmapFactory.decodeResource(resources, array.getResourceId(R.styleable.RoundImageView_bg_src, 0))
        viewType = array.getInt(R.styleable.RoundImageView_view_type, TYPE_CIRCLE)
        radious = array.getDimensionPixelSize(R.styleable.RoundImageView_radius, 0)
        borderColor = array.getColor(R.styleable.RoundImageView_border_color, Color.TRANSPARENT)
        borderWidth = array.getDimensionPixelSize(R.styleable.RoundImageView_border_width, 0)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthModel = MeasureSpec.getMode(widthMeasureSpec)
        var widhtSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightModel = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if(widthModel == MeasureSpec.EXACTLY) {
            viewWidth = widhtSize
        } else {
            if(srcBitmap == null) {
                return
            }
            var width = srcBitmap!!.width + paddingLeft + paddingRight
            if(widthModel == MeasureSpec.AT_MOST) {
                viewWidth = Math.min(width, widhtSize)
            } else {
                viewWidth = width
            }
        }
        if(heightModel == MeasureSpec.EXACTLY) {
            viewHeight = heightSize
        } else {
            if(srcBitmap == null) {
                return
            }
            var height = srcBitmap!!.height + paddingTop + paddingBottom
            if(heightModel == MeasureSpec.AT_MOST) {
                viewHeight = Math.min(height, heightSize)
            } else {
                viewHeight = height
            }
        }
        if(viewType == TYPE_CIRCLE) {
            var viewSize = Math.min(viewWidth , viewHeight)
            viewWidth = viewSize
            setMeasuredDimension(viewSize, viewSize)
            return
        }
        setMeasuredDimension(viewWidth + borderWidth * 2, viewHeight + borderWidth * 2)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(srcBitmap == null) return
        canvas!!.drawBitmap(drawBitmap(srcBitmap!!), 0F, 0F, null)
        if(viewType == TYPE_OVAL) return
        if(borderColor != 0 && borderWidth > 0) {
            var mPaint = Paint()
            mPaint.isAntiAlias = true
            mPaint!!.style = Paint.Style.STROKE
            mPaint!!.color = borderColor
            mPaint!!.strokeWidth = borderWidth.toFloat()
           if(viewType == TYPE_ROUND) {
              val rectf = RectF((borderWidth/2).toFloat(), (borderWidth/2).toFloat(), (viewWidth - borderWidth/2).toFloat(),
                      (viewHeight ).toFloat())
              canvas.drawRoundRect(rectf, radious.toFloat(), radious.toFloat(), mPaint)
           } else {
               canvas.drawCircle(((viewWidth ) / 2).toFloat(), ((viewWidth ) / 2).toFloat(),
                       ((viewWidth - borderWidth) / 2).toFloat(), mPaint)
           }
        }
    }

    fun drawBitmap(b: Bitmap) : Bitmap {
        var mPaint  = Paint()
        mPaint!!.isAntiAlias = true
        var bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        val mCanvas = Canvas(bitmap)
        if(viewType == TYPE_ROUND) {
            val rectF = RectF((borderWidth/2).toFloat(), (borderWidth/2).toFloat(), viewWidth.toFloat(), viewHeight.toFloat())
            mCanvas.drawRoundRect(rectF, radious.toFloat(), radious.toFloat(), mPaint)
        } else if(viewType == TYPE_OVAL) {
            val rectF = RectF(0F, 0F, viewWidth.toFloat(), viewHeight.toFloat())
            mCanvas.drawOval(rectF, mPaint)
        } else {
            mCanvas.drawCircle((viewWidth / 2).toFloat(), (viewWidth / 2).toFloat(), (viewWidth / 2).toFloat(), mPaint)
        }
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        mCanvas.drawBitmap(b, 0F, 0F, mPaint)
        return bitmap
    }
}