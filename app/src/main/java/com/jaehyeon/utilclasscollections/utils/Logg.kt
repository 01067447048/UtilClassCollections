package com.jaehyeon.utilclasscollections.utils

import android.util.Log

object Logg {
    private fun tag(): String {
        return Thread.currentThread().stackTrace[4].let {
            val link = "(${it.fileName}:${it.lineNumber})"
            val path = "App# ${it.className.substringAfterLast(".")}.${it.methodName}"
            if (path.length + link.length > 80) {
                "${path.take(80 - link.length)}...${link}"
            } else {
                "${path}${link}"
            }
        }
    }

    fun v(msg: String?) {
        if (BuildConfig.DEBUG)
            Log.v(tag(), "" + msg)
    }

    fun d(msg: String?) {
        if (BuildConfig.DEBUG)
            Log.d(tag(), "" + msg)
    }

    fun i(msg: String?) {
        if (BuildConfig.DEBUG)
            Log.i(tag(), "" + msg)
    }

    fun w(msg: String?) {
        if (BuildConfig.DEBUG)
            Log.w(tag(), "" + msg)
    }

    fun w(e: Throwable?) {
        if (BuildConfig.DEBUG)
            Log.w(tag(), "" + e?.localizedMessage)
        e?.printStackTrace()
    }

    fun w(e: Exception?) {
        if (BuildConfig.DEBUG)
            Log.w(tag(), "" + e?.localizedMessage)
        e?.printStackTrace()
    }

    fun e(msg: String?) {
        if (BuildConfig.DEBUG)
            Log.e(tag(), "" + msg)
    }
}