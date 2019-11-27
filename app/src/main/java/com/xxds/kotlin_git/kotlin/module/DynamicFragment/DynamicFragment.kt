package com.xxds.kotlin_git.kotlin.module.DynamicFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.FragmentListBinding
import com.xxds.kotlin_git.databinding.FragmentLoginBinding
import com.xxds.kotlin_git.kotlin.model.ui.EventUIAction
import com.xxds.kotlin_git.kotlin.model.ui.EventUIModel
import com.xxds.kotlin_git.kotlin.module.base.BaseListFragment
import com.xxds.kotlin_git.kotlin.ui.holder.EventHolder
import kotlinx.android.synthetic.main.fragment_list.*


 class DynamicFragment : BaseListFragment<FragmentListBinding, DynamicViewModel>()  {



    override fun getLayoutId(): Int {

        return R.layout.fragment_list
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)


    }

    override fun getViewModelClass(): Class<DynamicViewModel>  = DynamicViewModel::class.java

    override fun enableLoadMore(): Boolean = true

    override fun enableRefresh(): Boolean = true

    override fun getRecyclerView(): RecyclerView?  = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java )
    }
}