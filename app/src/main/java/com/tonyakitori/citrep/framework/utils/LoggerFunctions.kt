package com.tonyakitori.citrep.framework.utils

import android.util.Log
import com.tonyakitori.citrep.BuildConfig

fun Any.createDebugLog(tag: String = "LOGGERDEBUG") {
    if (BuildConfig.DEBUG) Log.d(tag, this.toString())
}

fun Any.createInfoLog(tag: String = "LOGGERINFO") {
    if (BuildConfig.DEBUG) Log.i(tag, this.toString())
}

fun Any.createWarnLog(tag: String = "LOGGERWARNING") {
    if (BuildConfig.DEBUG) Log.w(tag, this.toString())
}

fun Any.createErrorLog(tag: String = "LOGGERERROR") {
    if (BuildConfig.DEBUG) Log.e(tag, this.toString())
}

fun Any.createVerboseLog(tag: String = "LOGGERVERBOSE") {
    if (BuildConfig.DEBUG) Log.v(tag, this.toString())
}