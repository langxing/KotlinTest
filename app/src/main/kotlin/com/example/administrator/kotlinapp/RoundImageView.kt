package com.scrollerapplication

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.example.administrator.kotlinapp.R

/**
 * Created by chentao on 2017/2/13.
 * kotlin中文教程
 * https://github.com/huanglizhuo/kotlin-in-chinese
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

    fun setView(resourceId: Int) {
        if(resourceId > 0) {
            val bitmap = BitmapFactory.decodeResource(resources, resourceId)
            setView(bitmap)
        }
    }

    fun setView(url: String) {
        if(!TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).asBitmap().into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                    setView(resource!!)
                }
            })
        }
    }

    fun setView(bitmap: Bitmap) {
        if (bitmap == null) return
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
        //绘制外边框
        if(borderColor != 0 && borderWidth > 0) {
            var mPaint = Paint()
            mPaint.isAntiAlias = true
            mPaint!!.style = Paint.Style.STROKE
            mPaint!!.color = borderColor
            mPaint!!.strokeWidth = borderWidth.toFloat()
            val rectf = RectF((borderWidth/2).toFloat(), (borderWidth/2).toFloat(), (viewWidth + borderWidth/2).toFloat(), (viewHeight + borderWidth/2).toFloat())
           if(viewType == TYPE_ROUND) {
              canvas.drawRoundRect(rectf, (radious + borderWidth/2).toFloat(), (radious + borderWidth/2).toFloat(), mPaint)
           } else if(viewType == TYPE_OVAL) {
               canvas.drawOval(rectf, mPaint)
           } else {
               canvas.drawCircle(((viewWidth ) / 2).toFloat(), ((viewWidth ) / 2).toFloat(),
                       ((viewWidth - borderWidth/2) / 2).toFloat(), mPaint)
           }
        }
    }

    /**
     * 根据view类型,分别绘制圆形、矩形、弧形
     */
    fun drawBitmap(b: Bitmap) : Bitmap {
        var mPaint  = Paint()
        mPaint!!.isAntiAlias = true
        var bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        val mCanvas = Canvas(bitmap)
        val rectF = RectF((borderWidth).toFloat(), (borderWidth).toFloat(), viewWidth.toFloat(), viewHeight.toFloat())
        if(viewType == TYPE_ROUND) {
            mCanvas.drawRoundRect(rectF, radious.toFloat(), radious.toFloat(), mPaint)
        } else if(viewType == TYPE_OVAL) {
            mCanvas.drawOval(rectF, mPaint)
        } else {
            mCanvas.drawCircle((viewWidth / 2).toFloat(), (viewWidth / 2).toFloat(), ((viewWidth - borderWidth) / 2).toFloat(), mPaint)
        }
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        mCanvas.drawBitmap(b, 0F, 0F, mPaint)
        return bitmap
    }
}