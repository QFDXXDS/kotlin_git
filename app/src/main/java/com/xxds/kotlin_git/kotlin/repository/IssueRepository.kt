package com.xxds.kotlin_git.kotlin.repository

import android.app.Application
import com.xxds.kotlin_git.kotlin.repository.dao.IssueDao
import retrofit2.Retrofit
import javax.inject.Inject

class IssueRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application, private val issueDao: IssueDao) {

}