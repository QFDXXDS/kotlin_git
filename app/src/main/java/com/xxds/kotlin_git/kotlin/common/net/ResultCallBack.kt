package com.xxds.kotlin_git.kotlin.common.net

interface ResultCallBack<T> {

    fun onPage(first: Int, current: Int, last: Int) {

    }

    fun onSuccess(result: T?)

    fun onCacheSuccess(result: T?) {

    }

    fun onFailure() {

    }

}