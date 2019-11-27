package com.xxds.kotlin_git.kotlin.di

import com.xxds.kotlin_git.kotlin.di.annotation.ActivityScope
import com.xxds.kotlin_git.kotlin.module.StartNavigationActivity
import com.xxds.kotlin_git.kotlin.module.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract  class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [StartFragmentBindModule::class])
    abstract fun StartNavigationActivityInjector(): StartNavigationActivity



    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBindModule::class])
    abstract fun mainActivityInjector(): MainActivity

}