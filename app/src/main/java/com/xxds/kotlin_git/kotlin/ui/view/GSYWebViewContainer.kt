package com.xxds.kotlin_git.kotlin.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.MultiplePulseRing
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.common.utils.CommonUtils
import com.xxds.kotlin_git.kotlin.common.utils.dp


class GSYWebViewContainer : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val webView: GSYWebView = GSYWebView(context)

    val spinKit: SpinKitView

    init {
        webView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        var layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        val settings = webView.settings  //获取webView的setting
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.builtInZoomControls = false
        settings?.displayZoomControls = false
        settings?.domStorageEnabled = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings?.setAppCacheEnabled(true)

        addView(webView, layoutParams)


        spinKit = SpinKitView(context)
        layoutParams = RelativeLayout.LayoutParams(90.dp, 90.dp)
        spinKit.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
        spinKit.setPadding(15.dp)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        addView(spinKit, layoutParams)

        val animator = MultiplePulseRing()
        spinKit.setIndeterminateDrawable(animator)


        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                spinKit.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                spinKit.visibility = View.GONE

            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                CommonUtils.launchUrl(view!!.context, request!!.url!!.toString())
                return true
            }
        }


        webView.webViewClient = webViewClient

        webView.addJavascriptInterface(JsCallback(), "GSYWebView")
    }

    internal inner class JsCallback {

        @JavascriptInterface
        fun requestEvent(request: Boolean) {
            webView.requestIntercept = request
        }

    }


}