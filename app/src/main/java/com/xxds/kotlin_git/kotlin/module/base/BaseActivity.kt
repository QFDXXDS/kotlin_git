package com.xxds.kotlin_git.kotlin.module.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toolbar
import com.xxds.kotlin_git.R


abstract class BaseActivity: AppCompatActivity(), Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContentView(getLayoutId())
        initTitle()
    }
    fun initTitle() {

        setSupportActionBar(getToolBar())
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        getToolBar().title = getToolBarTitle()
        getToolBar().setOnMenuItemClickListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_default_menu,menu)
        return  true
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {

        when(p0?.itemId) {

            R.id.action_more -> {

                val pop = android.support.v7.widget.PopupMenu(this, getToolBar())
                pop.menuInflater.inflate(R.menu.toolbar_default_pop_menu, pop.menu)
                pop.gravity = Gravity.END
                pop.show()
            }

            R.id.action_browser -> {
                actionOpenByBrowser()
            }
            R.id.action_copy -> {
                actionCopy()
            }
            R.id.action_share -> {
                actionShare()
            }

        }

        return true
    }

    open fun actionOpenByBrowser() {

    }
    open fun actionCopy() {

    }
    open fun actionShare() {


    }



    abstract fun getToolBarTitle(): String

    abstract fun getToolBar(): android.support.v7.widget.Toolbar

    abstract fun getLayoutId(): Int

}