package com.xxds.kotlin_git.kotlin.module.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.LayoutInflaterCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.Navigation
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.model.AppGlobalModel
import com.xxds.kotlin_git.kotlin.module.DynamicFragment.DynamicFragment
import com.xxds.kotlin_git.kotlin.repository.IssueRepository
import com.xxds.kotlin_git.kotlin.repository.LoginRepository
import com.xxds.kotlin_git.kotlin.repository.RepoRepository
import com.xxds.kotlin_git.kotlin.ui.adapter.FragmentPagerViewAdater
import com.xxds.kotlin_git.kotlin.ui.view.GSYNavigationTabbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),HasSupportFragmentInjector, Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var globalModel: AppGlobalModel

    @Inject
    lateinit var  dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mainFragmentList: MutableList<Fragment>
    @Inject
    lateinit var mainTabModel: MutableList<NavigationTabBar.Model>
    @Inject
    lateinit var loginRepository: LoginRepository
    @Inject
    lateinit var repositoryRepository: RepoRepository
    @Inject
    lateinit var issueRepository: IssueRepository


    private val exitLogic = MainExitLogic(this)


    override fun onCreate(savedInstanceState: Bundle?) {

        LayoutInflaterCompat.setFactory2(layoutInflater,IconicsLayoutInflater2(delegate))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
        initToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun supportFragmentInjector(): AndroidInjector<Fragment> {

        return  dispatchingAndroidInjector
    }

    override fun onBackPressed() {

        exitLogic.backPress()
    }

    override fun onMenuItemClick(var1: MenuItem): Boolean  {

        return true
    }


    fun initViewPager() {

        home_view_pager.adapter = FragmentPagerViewAdater(mainFragmentList,supportFragmentManager)
        home_navigation_tab_bar.models = mainTabModel
        home_navigation_tab_bar.setViewPager(home_view_pager,0)
        home_view_pager.offscreenPageLimit = mainFragmentList.size

        home_navigation_tab_bar.doubleTouchListener = object : GSYNavigationTabbar.TabDoubleClickListener {

            override fun onDoubleClick(position: Int) {

               if (position == 0) {
                   val fragment = mainFragmentList[position] as DynamicFragment

               }
            }
        }
    }

    fun initToolbar() {

        setSupportActionBar(home_tool_bar)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)

        }


        home_tool_bar.setTitle(R.string.app_name)
//        home_tool_bar.setOnMenuItemClickListener(this)
    }

}

