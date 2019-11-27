package com.xxds.kotlin_git.kotlin.ui.holder

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.LayoutReposItemBinding
import com.xxds.kotlin_git.kotlin.model.ui.ReposUIModel
import com.xxds.kotlin_git.kotlin.ui.holder.base.DataBindingHolder
import kotlinx.android.synthetic.main.layout_repos_item.view.*

class ReposHolder(context: Context, private val v: View, dataBing: ViewDataBinding) : DataBindingHolder<ReposUIModel, LayoutReposItemBinding>(context, v, dataBing) {


    override fun onBind(model: ReposUIModel, position: Int, dataBing: LayoutReposItemBinding) {
        dataBing.reposUIModel = model
        v.repos_user_img.setOnClickListener {
//            PersonActivity.gotoPersonInfo(model.ownerName)
        }
    }

    companion object {
        const val ID = R.layout.layout_repos_item
    }
}