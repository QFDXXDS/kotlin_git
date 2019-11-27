package com.xxds.kotlin_git.kotlin.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.xxds.kotlin_git.R
import devlight.io.library.ntb.NavigationTabBar

/**
 * TODO: document your custom view class.
 */
class GSYNavigationTabbar : NavigationTabBar {

//    private var _exampleString: String? = null // TODO: use a default from R.string...
//    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
//    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...
//
//    private var textPaint: TextPaint? = null
//    private var textWidth: Float = 0f
//    private var textHeight: Float = 0f
//
//    /**
//     * The text to draw
//     */
//    var exampleString: String?
//        get() = _exampleString
//        set(value) {
//            _exampleString = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * The font color
//     */
//    var exampleColor: Int
//        get() = _exampleColor
//        set(value) {
//            _exampleColor = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * In the example view, this dimension is the font size.
//     */
//    var exampleDimension: Float
//        get() = _exampleDimension
//        set(value) {
//            _exampleDimension = value
//            invalidateTextPaintAndMeasurements()
//        }
//
//    /**
//     * In the example view, this drawable is drawn above the text.
//     */
//    var exampleDrawable: Drawable? = null

    var isTouchEnable = true
    var doubleTouchListener : TabDoubleClickListener? = null

    var gestureDetector = GestureDetector(context.applicationContext,object : GestureDetector.SimpleOnGestureListener() {

        override fun onDoubleTap(e: MotionEvent?): Boolean {

            doubleTouchListener?.onDoubleClick(mIndex)
            return super.onDoubleTap(e)
        }
    })

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isTouchEnable) {

            return true
        }
        super.onTouchEvent(event)

        gestureDetector.onTouchEvent(event)
        return  true
    }

    interface  TabDoubleClickListener {

        fun onDoubleClick(position: Int)
    }

}
