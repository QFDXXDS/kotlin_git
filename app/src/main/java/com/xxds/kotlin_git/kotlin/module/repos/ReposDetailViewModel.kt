package com.xxds.kotlin_git.kotlin.module.repos

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.xxds.kotlin_git.kotlin.common.net.ResultCallBack
import com.xxds.kotlin_git.kotlin.repository.RepoRepository
import javax.inject.Inject

class ReposDetailViewModel @Inject constructor(private val reposRepository: RepoRepository, private val application: Application): ViewModel() {

    val starredStatus = MutableLiveData<Boolean>()
    val watchedStatus = MutableLiveData<Boolean>()

    fun getReposStatus(userName: String, reposName: String) {
        reposRepository.getReposStatus(userName, reposName, object : ResultCallBack<HashMap<String, Boolean>> {
            override fun onSuccess(result: HashMap<String, Boolean>?) {
                result?.apply {
                    starredStatus.value = this[RepoRepository.STAR_KEY]
                    watchedStatus.value = this[RepoRepository.WATCH_KEY]
                }
            }

            override fun onFailure() {

            }
        })
    }

    fun changeStarStatus(context: Context, userName: String, reposName: String) {
        reposRepository.changeStarStatus(context, userName, reposName, starredStatus)
    }

    fun changeWatchStatus(context: Context, userName: String, reposName: String) {
        reposRepository.changeWatchStatus(context, userName, reposName, watchedStatus)
    }

    fun forkRepository(context: Context, userName: String, reposName: String) {
        reposRepository.forkRepository(context, userName, reposName)
    }
}