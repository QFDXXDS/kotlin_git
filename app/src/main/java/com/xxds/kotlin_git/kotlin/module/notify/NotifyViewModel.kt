package com.xxds.kotlin_git.kotlin.module.notify

import android.app.Application
import android.content.Context
import com.xxds.kotlin_git.kotlin.module.base.BaseViewModel
import com.xxds.kotlin_git.kotlin.repository.UserRepository
import javax.inject.Inject

class NotifyViewModel @Inject constructor(application: Application, private val userRepository: UserRepository): BaseViewModel(application) {

    var all: Boolean? = null
    var participating: Boolean? = null

    override fun loadDataByLoadMore() {

        loadData()
    }

    override fun loadDataByRefresh() {

        loadData()
    }
    private fun loadData() {
        userRepository.getNotify(all, participating, page, this)
    }

    fun setAllNotificationAsRead(context: Context) {
        userRepository.setAllNotificationAsRead(context)
    }

    fun setNotificationAsRead(id: String) {
        userRepository.setNotificationAsRead(id)
    }


}