package com.jaehyeon.utilclasscollections.extention

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showKeyboard(view: View) {
    view.requestFocus()
    CoroutineScope(Dispatchers.Main).launch {
        delay(300L)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(view, 0)
    }
}

fun Context.hideKeyboard(view: View) {
    view.isFocusable = false
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
}