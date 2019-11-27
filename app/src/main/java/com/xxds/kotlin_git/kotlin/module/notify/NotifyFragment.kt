package com.xxds.kotlin_git.kotlin.module.notify

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.shuyu.commonrecycler.BindSuperAdapterManager
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.FragmentNotifyBinding
import com.xxds.kotlin_git.kotlin.common.utils.EventUtils
import com.xxds.kotlin_git.kotlin.di.ARouterInjectable
import com.xxds.kotlin_git.kotlin.model.ui.EventUIModel
import com.xxds.kotlin_git.kotlin.module.ARouterAddress
import com.xxds.kotlin_git.kotlin.module.base.BaseListFragment
import com.xxds.kotlin_git.kotlin.ui.holder.EventHolder
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_notify.*
import javax.inject.Inject

@Route(path = ARouterAddress.NotifyFragment)
class NotifyFragment: BaseListFragment<FragmentNotifyBinding, NotifyViewModel>(), ARouterInjectable, NavigationTabBar.OnTabBarSelectedIndexListener {

    @Inject
    lateinit var TabList: MutableList<NavigationTabBar.Model>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        notify_tab_bar.models = TabList
        notify_tab_bar.onTabBarSelectedIndexListener = this
        notify_tab_bar.modelIndex = 0
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)

        val item = adapter?.dataList?.get(position) as EventUIModel
        getViewModel().setNotificationAsRead(item.threadId)
        EventUtils.evenAction(activity, item)
        notifyDelete(position, 1)
    }

    override fun getRecyclerView(): RecyclerView? {
        return  baseRecycler
    }

    override fun bindHolder(manager: BindSuperAdapterManager) {

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
    }

    override fun getViewModelClass(): Class<NotifyViewModel> {

        return  NotifyViewModel::class.java
    }

    override fun getLayoutId(): Int = R.layout.fragment_notify

    override fun enableRefresh(): Boolean {
        return true
    }

    override fun enableLoadMore(): Boolean {
        return true
    }

    override fun refreshComplete() {
        super.refreshComplete()

        notify_tab_bar.isTouchEnable = true
    }


    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {

    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
        notify_tab_bar.isTouchEnable = false
        when (index) {
            0 -> {
                getViewModel().all = null
                getViewModel().participating = null
            }
            1 -> {
                getViewModel().all = false
                getViewModel().participating = true
            }
            2 -> {
                getViewModel().all = true
                getViewModel().participating = false
            }
        }
        showRefresh()
    }

    fun setAllNotificationAsRead(context: Context) {
        getViewModel().setAllNotificationAsRead(context)
        adapter?.dataList?.clear()
        adapter?.notifyDataSetChanged()
    }
}