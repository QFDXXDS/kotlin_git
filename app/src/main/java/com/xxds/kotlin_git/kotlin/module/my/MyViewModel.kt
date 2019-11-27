package com.xxds.kotlin_git.kotlin.module.my

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.support.v4.content.ContextCompat
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.common.net.ResultCallBack
import com.xxds.kotlin_git.kotlin.model.AppGlobalModel
import com.xxds.kotlin_git.kotlin.module.base.BaseUserInfoViewModel
import com.xxds.kotlin_git.kotlin.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(private val userRepository: UserRepository, private val globalAppModel: AppGlobalModel, private val application: Application) : BaseUserInfoViewModel(userRepository, application) {

    val notifyColor = MutableLiveData<Int>()

    override fun loadDataByRefresh() {
        super.loadDataByRefresh()
        userRepository.getNotify(null, null, 1, object : ResultCallBack<ArrayList<Any>> {
            override fun onSuccess(result: ArrayList<Any>?) {
                notifyColor.value = if (result == null || result.size == 0) {
                    ContextCompat.getColor(application, R.color.subTextColor)
                } else {
                    ContextCompat.getColor(application, R.color.actionBlue)
                }
            }
            override fun onFailure() {
            }
        })

    }

    override fun getUserModel() = globalAppModel.userObservable
}