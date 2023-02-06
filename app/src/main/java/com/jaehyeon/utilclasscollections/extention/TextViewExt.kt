package com.jaehyeon.utilclasscollections.extention

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun TextView.changeTextColor(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, color))
}

fun TextView.changeTypeface(@StyleRes style: Int, @FontRes fontId: Int? = null) {
    fontId?.let {
        val typeface = ResourcesCompat.getFont(context, it)
        setTypeface(typeface)
    } ?: run { setTypeface(null, style) }
}