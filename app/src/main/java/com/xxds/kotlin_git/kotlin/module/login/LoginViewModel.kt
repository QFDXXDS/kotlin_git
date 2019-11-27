package com.xxds.kotlin_git.kotlin.module.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import android.view.View
import com.xxds.kotlin_git.kotlin.common.config.AppConfig
import com.xxds.kotlin_git.kotlin.common.utils.GSYPreference
import com.xxds.kotlin_git.kotlin.repository.LoginRepository
import org.jetbrains.anko.toast
import javax.inject.Inject
import com.xxds.kotlin_git.R

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME,default = "")
    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD,default = "")

    val username = ObservableField<String>()

    val password = ObservableField<String>()

    val loginResult = MutableLiveData<Boolean>()


    init {

        username.set(usernameStorage)
        password.set(passwordStorage)
    }

    fun login(context: Context) {

            loginRepository.login(context,username.get()!!,password.get()!!,loginResult)
    }

    fun onSubmitClick(view: View) {

        val  username = this.username.get()
        val  password = this.password.get()
        username?.apply {
            if (this.isEmpty()){

                view.context.toast(R.string.LoginNameTip)
                return
            }

        }
        password?.apply {
            if (this.isEmpty()){
                view.context.toast(R.string.LoginPWTip)
                return
            }
        }

        login(view.context)
    }

}