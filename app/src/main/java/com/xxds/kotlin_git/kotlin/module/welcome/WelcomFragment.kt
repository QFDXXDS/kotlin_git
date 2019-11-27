package com.xxds.kotlin_git.kotlin.module.welcome


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.FragmentWelcomBinding
import com.xxds.kotlin_git.kotlin.common.config.AppConfig
import com.xxds.kotlin_git.kotlin.common.net.GsonUtils
import com.xxds.kotlin_git.kotlin.common.utils.GSYPreference
import com.xxds.kotlin_git.kotlin.model.AppGlobalModel
import com.xxds.kotlin_git.kotlin.model.bean.User
import com.xxds.kotlin_git.kotlin.model.conversion.UserConversion
import com.xxds.kotlin_git.kotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcom.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class WelcomFragment : BaseFragment<FragmentWelcomBinding>() {

    private  var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO,"")
    @Inject
    lateinit var appGlobalModel: AppGlobalModel

    private var accessTokenStorage by GSYPreference(AppConfig.ACCESS_TOKEN, "")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcome_animation.speed = 5.0f
        Handler().postDelayed({
            goNext(view)
        },2000)
    }
    override fun getLayoutId(): Int {

        return R.layout.fragment_welcom
    }

    override fun onCreateView(mainView: View?) {


    }
    private fun goNext(view: View) {
        if (accessTokenStorage.isEmpty()) {
            navigationPopUpTo(view,null,R.id.action_nav_wel_to_login,false)
        } else {
            if (userInfoStorage.isEmpty()) {
                navigationPopUpTo(view,null,R.id.action_nav_wel_to_login,false)
            } else {

                var user = GsonUtils.parserJsonToBean(userInfoStorage,User::class.java)
                UserConversion.cloneDataFromUser(context,user,appGlobalModel.userObservable)
                navigationPopUpTo(view,null,R.id.action_nav_wel_to_main,true)
            }

        }

    }


}
