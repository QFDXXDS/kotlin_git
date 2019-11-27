package com.xxds.kotlin_git.kotlin.ui.view

import android.animation.StateListAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.FloatRange
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import com.xxds.kotlin_git.R
import org.jetbrains.anko.backgroundDrawable

class StateButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.support.v7.appcompat.R.attr.buttonStyle):
    AppCompatButton(context, attrs, defStyleAttr)  {


    private var mNormalBackground: GradientDrawable? = null
    private var mPressedBackground: GradientDrawable? = null
    private var mUnableBackground: GradientDrawable? = null


    private var states: Array<IntArray?>? = null
    lateinit var mStateBackground: StateListDrawable


    //text color
    private var mNormalTextColor = 0
    private var mPressedTextColor = 0
    private var mUnableTextColor = 0
    lateinit var mTextColorStateList: ColorStateList


    private var mDuration = 0

    //background color
    private var mNormalBackgroundColor = 0
    private var mPressedBackgroundColor = 0
    private var mUnableBackgroundColor = 0

    //stroke
    private var mStrokeDashWidth = 0f
    private var mStrokeDashGap = 0f
    private var mNormalStrokeWidth = 0
    private var mPressedStrokeWidth = 0
    private var mUnableStrokeWidth = 0
    private var mNormalStrokeColor = 0
    private var mPressedStrokeColor = 0
    private var mUnableStrokeColor = 0


    private var mRound: Boolean = false

    private var mRadius = 0f


    init {

    }
    fun setUp(attrs: AttributeSet?) {

        states = arrayOfNulls(4)

        var drawable = background
        if (drawable != null && drawable is StateListDrawable ) {

            mStateBackground = drawable
        } else {

            mStateBackground = StateListDrawable()
        }

        mNormalBackground = GradientDrawable()
        mPressedBackground = GradientDrawable()
        mUnableBackground = GradientDrawable()

        states!![0] = intArrayOf(android.R.attr.state_enabled,android.R.attr.state_pressed)
        states!![1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
        states!![3] = intArrayOf(-android.R.attr.state_enabled)
        states!![2] = intArrayOf(android.R.attr.state_enabled)

        val a = context.obtainStyledAttributes(attrs, R.styleable.StateButton)

        mTextColorStateList = textColors
        val mDefaultNormalTextColor = mTextColorStateList.getColorForState(states!![2], currentTextColor)
        val mDefaultPressedTextColor = mTextColorStateList.getColorForState(states!![0], currentTextColor)
        val mDefaultUnableTextColor = mTextColorStateList.getColorForState(states!![3], currentTextColor)


        mNormalTextColor = a.getColor(R.styleable.StateButton_normalTextColor, mDefaultNormalTextColor)
        mPressedTextColor = a.getColor(R.styleable.StateButton_pressedTextColor, mDefaultPressedTextColor)
        mUnableTextColor = a.getColor(R.styleable.StateButton_unableTextColor, mDefaultUnableTextColor)
        setTextColor()

        //set animation duration

        mDuration = a.getInteger(R.styleable.StateButton_animationDuration,mDuration)
        mStateBackground.setEnterFadeDuration(mDuration)
        mStateBackground.setExitFadeDuration(mDuration)

        mNormalBackgroundColor = a.getColor(R.styleable.StateButton_normalBackgroundColor,0)
        mPressedBackgroundColor = a.getColor(R.styleable.StateButton_pressedBackgroundColor,0)
        mUnableBackgroundColor = a.getColor(R.styleable.StateButton_unableBackgroundColor,0)
        mNormalBackground!!.setColor(mNormalBackgroundColor)
        mPressedBackground!!.setColor(mPressedBackgroundColor)
        mUnableBackground!!.setColor(mUnableBackgroundColor)


        mStrokeDashWidth = a.getDimensionPixelSize(R.styleable.StateButton_strokeDashWidth,0).toFloat()
        mStrokeDashGap = a.getDimensionPixelSize(R.styleable.StateButton_strokeDashWidth,0).toFloat()
        mNormalStrokeWidth = a.getDimensionPixelSize(R.styleable.StateButton_normalStrokeWidth,0)
        mPressedStrokeWidth = a.getDimensionPixelSize(R.styleable.StateButton_pressedStrokeWidth,0)
        mUnableStrokeWidth = a.getDimensionPixelSize(R.styleable.StateButton_unableStrokeWidth,0)

        mNormalStrokeColor = a.getColor(R.styleable.StateButton_normalStrokeColor,0)
        mPressedStrokeColor = a.getColor(R.styleable.StateButton_pressedStrokeColor,0)
        mUnableStrokeColor = a.getColor(R.styleable.StateButton_unableStrokeColor,0)
        setStroke()

        mStateBackground.addState(states!![0],mPressedBackground)
        mStateBackground.addState(states!![1], mPressedBackground)
        mStateBackground.addState(states!![3], mUnableBackground)
        mStateBackground.addState(states!![2], mNormalBackground)

        backgroundDrawable = mStateBackground

        a.recycle()

    }

    private fun setTextColor() {

        val colors = intArrayOf(mPressedTextColor, mPressedTextColor, mNormalTextColor, mUnableTextColor)
        mTextColorStateList = ColorStateList(states,colors)

        setTextColor(mTextColorStateList)
    }

    private fun setStroke() {

        setStroke(mNormalBackground!!,mNormalStrokeColor,mNormalStrokeWidth)
        setStroke(mPressedBackground!!,mPressedBackgroundColor,mPressedStrokeWidth)
        setStroke(mUnableBackground!!,mUnableStrokeColor,mUnableStrokeWidth)
    }

    private fun setStroke(mBackgroud: GradientDrawable,mStrokeColor:Int, mStrokeWith: Int) {

        mBackgroud.setStroke(mStrokeWith,mStrokeColor,mStrokeDashWidth,mStrokeDashGap)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


    }

    fun setRound(round: Boolean) {

        this.mRound = round
        val height = measuredHeight
        if (mRound) {

            setRadius(height / 2f)
        }

    }

    fun setRadius(radii: FloatArray) {

        mNormalBackground!!.cornerRadii = radii
        mPressedBackground!!.cornerRadii = radii
        mUnableBackground!!.cornerRadii = radii
    }

    fun setRadius( radius: Float) {

        this.mRadius = radius
        mNormalBackground!!.cornerRadius = mRadius
        mPressedBackground!!.cornerRadius = mRadius
        mUnableBackground!!.cornerRadius = mRadius
    }







}