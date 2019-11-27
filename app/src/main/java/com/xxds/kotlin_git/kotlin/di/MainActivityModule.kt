package com.xxds.kotlin_git.kotlin.di

import android.app.Application
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.common.style.GSYIconfont
import com.xxds.kotlin_git.kotlin.module.DynamicFragment.DynamicFragment
import com.xxds.kotlin_git.kotlin.module.my.MyFragment
import com.xxds.kotlin_git.kotlin.module.trend.TrendFragment
import dagger.Module
import dagger.Provides
import devlight.io.library.ntb.NavigationTabBar

@Module
class MainActivityModule {

    @Provides
    fun providerMainFragmentList(): List<Fragment> {

        return listOf(DynamicFragment(),TrendFragment(),MyFragment())
    }

    @Provides
    fun providerMainTabModel(application: Application): List<NavigationTabBar.Model> {
        return listOf(

            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(GSYIconfont.Icon.GSY_MAIN_DT)
                    .color(ContextCompat.getColor(application, R.color.subTextColor))
                    .sizeDp(20),
                Color.parseColor("#00000000"))
                .title(application.getString(R.string.tabDynamic))
                .build(),
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(GSYIconfont.Icon.GSY_MAIN_QS)
                    .color(ContextCompat.getColor(application, R.color.subTextColor))
                    .sizeDp(20),
                Color.parseColor("#00000000"))
                .title(application.getString(R.string.tabRecommended))
                .build(),
            NavigationTabBar.Model.Builder(
                IconicsDrawable(application)
                    .icon(GSYIconfont.Icon.GSY_MAIN_MY)
                    .color(ContextCompat.getColor(application, R.color.subTextColor))
                    .sizeDp(20),
                Color.parseColor("#00000000"))
                .title(application.getString(R.string.tabMy))
                .build()
        )

    }
}