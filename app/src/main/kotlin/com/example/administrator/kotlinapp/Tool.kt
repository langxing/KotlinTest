package com.example.administrator.kotlinapp

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast

/**
 * Created by Administrator on 2017/2/13.
 */

/**
 * 显示键盘
 */
fun EditText.showSoftKeyboard() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    findFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * 隐藏键盘
 */
fun EditText.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
    (parent as View).isFocusableInTouchMode = true
    clearFocus()
}

/**
 * 在中间显示Tost
 */
fun Context.showCenterToast(message : String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

/**
 * 短暂的消息提示
 */
fun Context.showToastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * 稍长的消息提示
 */
fun Context.showToastLong(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * 获取屏幕宽度
 */
fun Context.screenWidth() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
fun Context.screenHeight() = resources.displayMetrics.heightPixels