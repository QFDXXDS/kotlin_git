package com.xxds.kotlin_git.kotlin.common.net

import android.content.Context
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.ui.view.LoadingDialog

abstract class ResultProgressObserver<T>(private val context: Context, private val needloading: Boolean = true): ResultTipObserver<T>(context) {


    private var loadingDialog: LoadingDialog ? = null

    private var loadingText: String? = null

    constructor(context: Context, loadingText: String?) : this(context) {
        this.loadingText = loadingText
    }

    override fun onRequestStart() {
        super.onRequestStart()
        if (needloading) {
            showLoading()
        }
    }

    override fun onRequestEnd() {
        super.onRequestEnd()
        dismissLoading()
    }

    private fun getLoadingText(): String {
        return if (loadingText.isNullOrBlank()) context.getString(R.string.loading) else loadingText!!
    }

    private fun showLoading() {
        dismissLoading()
        loadingDialog = LoadingDialog.showDialog(context, getLoadingText(), false, null)
    }

    private fun dismissLoading() {
        loadingDialog?.apply {
            if (this.isShowing) {
                this.dismiss()
            }
        }
    }
}