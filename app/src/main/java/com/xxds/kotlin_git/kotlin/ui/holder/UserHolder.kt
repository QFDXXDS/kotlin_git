package com.xxds.kotlin_git.kotlin.ui.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.LayoutUserItemBinding
import com.xxds.kotlin_git.kotlin.model.ui.UserUIModel
import com.xxds.kotlin_git.kotlin.ui.holder.base.DataBindingHolder

/**
 * 用户item
 */
class UserHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<UserUIModel, LayoutUserItemBinding>(context, v, dataBing) {

    override fun onBind(model: UserUIModel, position: Int, dataBing: LayoutUserItemBinding) {
        dataBing.userUIModel = model
    }

    companion object {
        const val ID = R.layout.layout_user_item
    }
}