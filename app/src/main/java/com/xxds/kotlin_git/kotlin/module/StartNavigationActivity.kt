package com.xxds.kotlin_git.kotlin.module

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.xxds.kotlin_git.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class StartNavigationActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_navigation)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onBackPressed() {

//      返回FragmentManager里面当前活动的主导航Fragment。
        val fragment = supportFragmentManager.primaryNavigationFragment
        if (fragment is NavHostFragment) {
            if(fragment.navController.currentDestination?.id == R.id.loginFragment) {
                super.onBackPressed()
            }
        }
    }
}