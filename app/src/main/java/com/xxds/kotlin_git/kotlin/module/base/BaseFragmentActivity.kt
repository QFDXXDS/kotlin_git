package com.xxds.kotlin_git.kotlin.module.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.PopupMenu
import android.widget.Toolbar
import com.xxds.kotlin_git.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_fragment_container.*
import javax.inject.Inject

abstract class BaseFragmentActivity : BaseActivity(),HasSupportFragmentInjector, Toolbar.OnMenuItemClickListener,PopupMenu.OnMenuItemClickListener{


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var fragment: BaseFragment<*>? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        fragment = getInitFragment()
        addFragment(fragment!!, R.id.activity_fragment_container_id)
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_fragment_container
    }

    override fun actionOpenByBrowser() {
        fragment?.actionOpenByBrowser()
    }

    override fun actionCopy() {
        fragment?.actionCopy()
    }

    override fun actionShare() {
        fragment?.actionShare()
    }

    override fun getToolBar(): android.support.v7.widget.Toolbar = activity_fragment_container_toolbar

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    abstract fun getInitFragment(): BaseFragment<*>
}


fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction {
        add(frameId,fragment)
    }
}
fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}