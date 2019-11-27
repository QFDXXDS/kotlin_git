package com.xxds.kotlin_git.kotlin.module.base


import android.app.Application
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuyu.commonrecycler.BindSuperAdapterManager

import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.FragmentUserInfoBinding
import com.xxds.kotlin_git.databinding.LayoutUserHeaderBinding
import com.xxds.kotlin_git.kotlin.model.ui.EventUIModel
import com.xxds.kotlin_git.kotlin.model.ui.UserUIModel
import com.xxds.kotlin_git.kotlin.repository.UserRepository
import com.xxds.kotlin_git.kotlin.ui.holder.EventHolder
import com.xxds.kotlin_git.kotlin.ui.holder.UserHolder
import com.xxds.kotlin_git.kotlin.ui.holder.base.GSYDataBindingComponent
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.jetbrains.anko.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
abstract class BaseUserInfoFragment<T : BaseUserInfoViewModel> : BaseListFragment<FragmentUserInfoBinding,T>() {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_info, container, false)
//    }

    override fun getLayoutId(): Int = R.layout.fragment_user_info

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)

        getViewModel().login = getLoginName()
    }



    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)

        val item = adapter?.dataList?.get(position)
        when(item) {

            is EventUIModel -> {

//                EventUtils.evenAction(activity, adapter?.dataList?.get(position) as EventUIModel)

            }
            is UserUIModel -> {

//                PersonActivity.gotoPersonInfo(item.login!!)

            }
        }
    }

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = true

    override fun getRecyclerView(): RecyclerView? = fragment_my_recycler

//    override fun getViewModelClass(): Class<BaseUserInfoViewModel> = BaseUserInfoViewModel::class.java

    override fun bindHolder(manager: BindSuperAdapterManager) {

        val binding: LayoutUserHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_user_header,
            null, false, GSYDataBindingComponent()
        )
        binding.userUIModel = getViewModel().getUserModel()
        binding.baseUserViewModel = getViewModel()
        binding.userHeaderNotify.visibility = View.GONE
        manager.addHeaderView(binding.root)
        bindHeader(binding)

        manager.bind(EventUIModel::class.java, EventHolder.ID, EventHolder::class.java)
        manager.bind(UserUIModel::class.java, UserHolder.ID, UserHolder::class.java)
    }

    open fun bindHeader(binding: LayoutUserHeaderBinding){

    }

    abstract fun getLoginName(): String?


}


abstract class BaseUserInfoViewModel constructor(private val userRepository: UserRepository, private val application: Application) : BaseViewModel(application) {

    val foucsIcon = ObservableField<String>()

    var login: String? = null

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(null, this, login)
    }

    override fun loadDataByLoadMore() {
        if (getUserModel().type == "Organization") {
            userRepository.getOrgMembers(getUserModel().login, this, page)
        } else {
            userRepository.getUserEvent(getUserModel().login, this, page)
        }
    }


    fun onTabIconClick(v: View?) {
        getUserModel().login?.apply {
            when (v?.id) {
                R.id.user_header_repos -> {
//                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
//                    +application.getString(R.string.FollowedText), GeneralEnum.UserRepository, true)
                }
                R.id.user_header_fan -> {
//                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
//                    +application.getString(R.string.FollowersText), GeneralEnum.UserFollower)
                }
                R.id.user_header_focus -> {
//                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
//                    +application.getString(R.string.FollowedText), GeneralEnum.UserFollowed)
                }
                R.id.user_header_star -> {
//                    GeneralListActivity.gotoGeneralList(this, "", login ?: ""+" "
//                    +application.getString(R.string.FollowedText), GeneralEnum.UserStar)
                }
                R.id.user_header_honor -> {
                    v.context.toast(R.string.user100Honor)
                }
            }
        }
    }

    abstract fun getUserModel(): UserUIModel


    open fun onFocusClick(v: View?) {

    }
}