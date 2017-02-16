package com.example.administrator.kotlinapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

/**
 * Created by chentao on 2017/2/16.
 * 设置view
 */
class SettingView : View {
    constructor(context: Context): super(context) {
        init(context)
    }
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        init(context, attributeSet)
    }
    constructor(context: Context, attributeSet: AttributeSet, defStyle : Int): super(context, attributeSet, defStyle) {
        init(context, attributeSet, defStyle)
    }

    private fun  init(context: Context, attributeSet: AttributeSet? = null , defStyle: Int = 0) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_setting, this, false)
    }

}