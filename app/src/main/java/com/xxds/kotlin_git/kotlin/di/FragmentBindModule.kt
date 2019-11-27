package com.xxds.kotlin_git.kotlin.di

import com.xxds.kotlin_git.kotlin.module.DynamicFragment.DynamicFragment
import com.xxds.kotlin_git.kotlin.module.login.LoginFragment
import com.xxds.kotlin_git.kotlin.module.my.MyFragment
import com.xxds.kotlin_git.kotlin.module.trend.TrendFragment
import com.xxds.kotlin_git.kotlin.module.welcome.WelcomFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class MainFragmentBindModule {
    @ContributesAndroidInjector
    abstract fun contributeDynamicFragment(): DynamicFragment

    @ContributesAndroidInjector
    abstract fun contributeTrendFragment(): TrendFragment

    @ContributesAndroidInjector
    abstract fun contributeMyFragment(): MyFragment
}


@Module
abstract class StartFragmentBindModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeFragment(): WelcomFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

}