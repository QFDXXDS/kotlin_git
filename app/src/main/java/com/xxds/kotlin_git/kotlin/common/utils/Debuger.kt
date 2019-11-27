package com.xxds.kotlin_git.kotlin.common.utils

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import com.xxds.kotlin_git.BuildConfig

//
object Debuger {

    private val LOG_TAG = "GSYGithubAppKotlin"
//  如果在debugMode的就是BuildConfig.DEBUG有效
    var debugMode = BuildConfig.DEBUG

    fun enable() {
        debugMode = true
    }

    fun disable() {
        debugMode = false
    }

    fun printfLog(tag: String, log: String?) {
        if (debugMode && log != null) {
            if (!TextUtils.isEmpty(log))
                Log.i(tag, log)
        }
    }

    fun printfLog(log: String) {
        printfLog(LOG_TAG, log)
    }

    fun printfWarning(tag: String, log: String?) {
        if (debugMode && log != null) {
            if (!TextUtils.isEmpty(log))
                Log.w(tag, log)
        }
    }

    fun printfWarning(log: String) {
        printfWarning(LOG_TAG, log)
    }

    fun printfError(log: String) {
        if (debugMode) {

//            TextUtils 系统库工具类，可以判断是否是空库
            if (!TextUtils.isEmpty(log))
                Log.e(LOG_TAG, log)
        }
    }

    fun printfError(Tag: String, log: String) {
        if (debugMode) {
            if (!TextUtils.isEmpty(log))
                Log.e(Tag, log)
        }
    }

    fun printfError(log: String, e: Exception) {
        if (debugMode) {
            if (!TextUtils.isEmpty(log))
                Log.e(LOG_TAG, log)
//          exception 可以打印栈踪迹
            e.printStackTrace()
        }
    }

    fun Toast(activity: Activity, log: String) {
        if (debugMode) {
            if (!TextUtils.isEmpty(log))
                android.widget.Toast.makeText(activity, log, android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}