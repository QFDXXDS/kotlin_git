package com.xxds.kotlin_git.kotlin.module.repos

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.LayoutInflaterCompat
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.di.ARouterInjectable
import com.xxds.kotlin_git.kotlin.module.ARouterAddress
import com.xxds.kotlin_git.kotlin.module.base.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import devlight.io.library.ntb.NavigationTabBar
import javax.inject.Inject

class ReposDetailActivity : BaseActivity(), HasSupportFragmentInjector,ARouterInjectable {

    companion object {

        fun gotoReposDetail(userName: String, reposName: String) {
            getRouterNavigation(ARouterAddress.ReposDetailActivity, userName, reposName).navigation()
        }

        fun getRouterNavigation(uri: String, userName: String, reposName: String): Postcard {
            return ARouter.getInstance()
                .build(uri)
                .withString("userName", userName)
                .withString("reposName", reposName)
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    /**
     * tab列表
     */
    @Inject
    lateinit var tabModel: MutableList<NavigationTabBar.Model>


    private lateinit var viewModel: ReposDetailViewModel

    private lateinit var fragmentReadme: ReposReadmeFragment
    private lateinit var fragmentActionList: ReposActionListFragment
    private lateinit var fragmentFileList: ReposFileListFragment
    private lateinit var fragmentIssueList: ReposIssueListFragment


    override fun onCreate(savedInstanceState: Bundle?) {

        LayoutInflaterCompat.setFactory2()
        super.onCreate(savedInstanceState)

    }


}
