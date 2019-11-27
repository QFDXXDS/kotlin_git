package com.xxds.kotlin_git.kotlin.module.notify

import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.di.ARouterInjectable
import com.xxds.kotlin_git.kotlin.module.ARouterAddress
import com.xxds.kotlin_git.kotlin.module.base.BaseFragment
import com.xxds.kotlin_git.kotlin.module.base.BaseFragmentActivity

@Route(path = ARouterAddress.NotifyActivity)
class NotifyActivity : BaseFragmentActivity(), ARouterInjectable {


    private var fragment: NotifyFragment? = null

    companion object {

        fun gotoNotify() {
            getRouterNavigation(ARouterAddress.NotifyActivity).navigation()
        }

        fun getRouterNavigation(uri: String): Postcard {

            return ARouter.getInstance().build(uri)
        }
    }
    override fun getToolBarTitle(): String {

        return getString(R.string.notify)
    }
    override fun getInitFragment(): NotifyFragment {
        fragment = getRouterNavigation(ARouterAddress.NotifyFragment).navigation() as NotifyFragment
        return fragment!!
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_notify_menu,menu)
        return true
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_read -> {
                fragment?.setAllNotificationAsRead(this)
            }
        }
        return true
    }




}