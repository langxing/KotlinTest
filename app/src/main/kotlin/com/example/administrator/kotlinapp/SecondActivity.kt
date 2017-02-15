package com.example.administrator.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.toast

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        showCenterToast(screenWidth().toString())
        showToastLong(screenHeight().toString())
        edit_query.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            edit_query.showSoftKeyboard()
            true
        })
        text.setOnClickListener {
            edit_query.hideSoftKeyboard()
        }
        image.setView(R.mipmap.purse_index)
    }
}
