package com.xxds.kotlin_git.kotlin.module.trend

import android.app.Application
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.module.base.BaseViewModel
import com.xxds.kotlin_git.kotlin.repository.RepoRepository
import javax.inject.Inject

class TrendViewModel @Inject constructor(private val repository: RepoRepository, application: Application ): BaseViewModel(application) {


    val sortData: List<List<String>> = listOf(
        application.resources.getStringArray(R.array.trend_language).toList(),
        application.resources.getStringArray(R.array.trend_since).toList())

    val sortValue: List<List<String>> = listOf(
        application.resources.getStringArray(R.array.trend_language_data).toList(),
        application.resources.getStringArray(R.array.trend_since_data).toList())

    var sortType = arrayListOf(sortValue[0][0], sortValue[1][0])

    override fun loadDataByLoadMore() {

        loadData()
    }

    override fun loadDataByRefresh() {
        loadData()
    }

    private fun loadData() {

        clearWhenRefresh()
        repository.getTrend(this, sortType[0], sortType[1])
    }
}
