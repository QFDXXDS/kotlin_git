package com.xxds.kotlin_git.kotlin.model

import com.xxds.kotlin_git.kotlin.model.ui.UserUIModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppGlobalModel @Inject constructor() {

    val userObservable = UserUIModel()
}