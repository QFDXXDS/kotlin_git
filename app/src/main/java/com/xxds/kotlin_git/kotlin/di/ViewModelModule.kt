package com.xxds.kotlin_git.kotlin.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.xxds.kotlin_git.kotlin.GSYViewModelFactory
import com.xxds.kotlin_git.kotlin.di.annotation.ViewModelKey
import com.xxds.kotlin_git.kotlin.module.DynamicFragment.DynamicViewModel
import com.xxds.kotlin_git.kotlin.module.login.LoginViewModel
import com.xxds.kotlin_git.kotlin.module.my.MyViewModel
import com.xxds.kotlin_git.kotlin.module.trend.TrendViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DynamicViewModel::class)
    abstract fun bindDynamicViewModel(dynamicViewModel: DynamicViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TrendViewModel::class)
    abstract fun bindTrendViewModel(trendViewModel: TrendViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel::class)
    abstract fun bindMyViewModel(myViewModel: MyViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: GSYViewModelFactory): ViewModelProvider.Factory
}