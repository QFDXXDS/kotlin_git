package com.xxds.kotlin_git.kotlin.common.utils

import android.content.Context
import android.widget.AdapterView
import com.xxds.kotlin_git.kotlin.model.ui.EventUIAction
import com.xxds.kotlin_git.kotlin.model.ui.EventUIModel
import com.xxds.kotlin_git.kotlin.module.ARouterAddress.IssueDetailActivity
import com.xxds.kotlin_git.kotlin.module.ARouterAddress.PersonActivity
import com.xxds.kotlin_git.kotlin.module.ARouterAddress.PushDetailActivity
import com.xxds.kotlin_git.kotlin.module.ARouterAddress.ReposDetailActivity

object EventUtils {

    fun evenAction(context: Context?, eventUIModel: EventUIModel?) {

        /*
        *when 后跟is 要搭配 else*/
//        when (event) {
//
//            is ->
//            else ->
//        }

        when (eventUIModel?.actionType) {
            EventUIAction.Person -> {
//                PersonActivity.gotoPersonInfo(eventUIModel.owner)
            }
            EventUIAction.Repos -> {
//                ReposDetailActivity.gotoReposDetail(eventUIModel.owner, eventUIModel.repositoryName)
            }
            EventUIAction.Issue -> {
//                IssueDetailActivity.gotoIssueDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.IssueNum)
            }
            EventUIAction.Push -> {
                if (eventUIModel.pushSha.size == 1) {
//                    PushDetailActivity.gotoPushDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.pushSha[0])
                } else {
//                    context?.showOptionSelectDialog(eventUIModel.pushShaDes, AdapterView.OnItemClickListener { dialog, _, _, position ->

                        //                        dialog.dismiss()
//                        PushDetailActivity.gotoPushDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.pushSha[position])
//                    })
                }
            }
            EventUIAction.Release -> {
            }
        }
    }
}