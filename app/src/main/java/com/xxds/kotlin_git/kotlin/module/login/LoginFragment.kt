package com.xxds.kotlin_git.kotlin.module.login


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.AppComponentFactory
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tencent.bugly.proguard.aa

import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.FragmentLoginBinding
import com.xxds.kotlin_git.kotlin.module.base.BaseFragment
import org.jetbrains.anko.toast
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment: BaseFragment<FragmentLoginBinding> () {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        exitFull()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(mainView: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        binding?.loginViewModel = loginViewModel

        loginViewModel.loginResult.observe(this, Observer { result ->
            //根据结果返回，跳转主页
            if (result != null && result == true) {
                navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
            } else {
                activity?.toast(R.string.LoginFailTip)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }
}