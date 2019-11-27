package com.xxds.kotlin_git.kotlin.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.xxds.kotlin_git.kotlin.GSYGithubApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {


    fun init(githubApp: GSYGithubApplication) {
        DaggerAppComponent.builder().application(githubApp)
            .build().inject(githubApp)

        githubApp.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun handleActivity(activity: Activity) {
//        is  isinstanceof 类型判断
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is ARouterInjectable) {
            ///注入ARouter参数
            ARouter.getInstance().inject(activity)
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        savedInstanceState: Bundle?) {

                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }
                        if (f is ARouterInjectable) {
                            ///注入ARouter参数
                            ARouter.getInstance().inject(f)
                        }
                    }
                }, true
            )
        }
    }
}