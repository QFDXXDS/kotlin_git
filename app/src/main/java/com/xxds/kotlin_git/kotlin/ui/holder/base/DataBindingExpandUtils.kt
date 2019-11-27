package com.xxds.kotlin_git.kotlin.ui.holder.base

import android.databinding.BindingAdapter
import android.databinding.DataBindingComponent
//import android.databinding.DataBindingComponent
import android.graphics.Point
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.view.IconicsImageView
import com.xxds.kotlin_git.kotlin.common.utils.CommonUtils
import com.xxds.kotlin_git.kotlin.common.utils.dp

class DataBindingExpandUtils {

    companion object {

        /**
         * 高斯模糊图片加载
         */
        @BindingAdapter("image_blur")
        fun loadImageBlur(view: ImageView, url: String?) {
            CommonUtils.loadImageBlur(view, url ?: "")
        }

        /**
         * 圆形用户头像加载
         */
        @BindingAdapter("userHeaderUrl", "userHeaderSize", requireAll = false)
        fun loadImage(view: ImageView, url: String?, size: Int = 50) {
            CommonUtils.loadUserHeaderImage(view, url ?: "", Point(size.dp, size.dp))
        }

        /**
         * webView url加载拓展
         */
        @BindingAdapter("webViewUrl")
        fun webViewUrl(view: View?, url: String?) {
            view?.apply {
//                webView.isVerticalScrollBarEnabled = false
//                webView.loadUrl(url)
            }

        }

//        fun webViewUrl(view: GSYWebViewContainer?, url: String?) {
//            view?.apply {
//                webView.isVerticalScrollBarEnabled = false
//                webView.loadUrl(url)
//            }
//
//        }

        /**
         * markdown数据处理显示
         */
        @BindingAdapter("markdownText", "style", requireAll = false)
        fun markdownText(view: TextView?, text: String?, style: String? = "default") {
            view?.apply {
//                Markwon.setMarkdown(view, MarkDownConfig.getConfig(view.context), text ?: "")
            }
        }

        /**
         * EditText 按键监听
         */
        @BindingAdapter("keyListener")
        fun editTextKeyListener(view: EditText?, listener: View.OnKeyListener) {
            view?.apply {
                this.setOnKeyListener(listener)
            }
        }

        /**
         * Iconics ImageView 图标加载
         */
        @BindingAdapter("iiv_icon", "iiv_color", requireAll = false)
        fun editTextKeyListener(view: IconicsImageView?, value: String?, colorId: Int?) {
            if (view == null || value == null) {
                return
            }
            val drawable = IconicsDrawable(view.context)
                .icon(value)
            colorId?.apply {
                drawable.color(colorId)
            }
            view.icon = drawable
        }
    }
}


/**
 * 加载 DataBinding 的拓展适配器,
 */

//    需要编译成功才能正常使用
class GSYDataBindingComponent : DataBindingComponent {
    override fun getCompanion(): DataBindingExpandUtils.Companion = DataBindingExpandUtils
}

