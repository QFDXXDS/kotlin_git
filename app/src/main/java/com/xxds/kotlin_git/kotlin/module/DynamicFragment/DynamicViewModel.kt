package com.xxds.kotlin_git.kotlin.module.DynamicFragment

import android.app.Application
import com.xxds.kotlin_git.kotlin.module.base.BaseViewModel
import com.xxds.kotlin_git.kotlin.repository.UserRepository
import javax.inject.Inject

class DynamicViewModel @Inject constructor(private  val userRepository: UserRepository, application: Application) : BaseViewModel(application) {

    override fun refresh() {

        if (isLoading()){
            return
        }
        super.refresh()
    }

    override fun loadDataByRefresh() {

        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        clearWhenRefresh()
        userRepository.getReceivedEvent(this,page)
    }

}