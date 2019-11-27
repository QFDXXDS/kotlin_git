package com.xxds.kotlin_git.kotlin.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.util.Base64
import com.xxds.kotlin_git.kotlin.common.config.AppConfig
import com.xxds.kotlin_git.kotlin.common.net.FlatMapResponse2Result
import com.xxds.kotlin_git.kotlin.common.net.FlatMapResult2Response
import com.xxds.kotlin_git.kotlin.common.net.ResultProgressObserver
import com.xxds.kotlin_git.kotlin.common.net.RetrofitFactory
import com.xxds.kotlin_git.kotlin.common.utils.Debuger
import com.xxds.kotlin_git.kotlin.common.utils.GSYPreference
import com.xxds.kotlin_git.kotlin.model.bean.LoginRequestModel
import com.xxds.kotlin_git.kotlin.model.bean.User
import com.xxds.kotlin_git.kotlin.module.StartNavigationActivity
import com.xxds.kotlin_git.kotlin.service.LoginService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import org.jetbrains.anko.clearTask
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepository @Inject constructor(private val retrofit: Retrofit, private val userRepository: UserRepository) {


    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userBasicCodeStorage: String by GSYPreference(AppConfig.USER_BASIC_CODE, "")

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    /**
     * 获取token
     */
    fun getTokenObservable(): Observable<String> {

//        通过client_ID获取Token
        return retrofit.create(LoginService::class.java)
            .authorizations(LoginRequestModel.generate())
            .flatMap {
                FlatMapResponse2Result(it)
            }.map {
                it.token ?: ""
            }.doOnNext {
                Debuger.printfLog("token $it")
                accessTokenStorage = it
            }.onErrorResumeNext(Function<Throwable, Observable<String>> { t ->
                Debuger.printfLog("token onErrorResumeNext ")
                clearTokenStorage()
                Observable.error(t)
            })
    }

    /**
     * 登录
     */
    fun login(context: Context, username: String, password: String, token: MutableLiveData<Boolean>) {

        clearTokenStorage()

        val type = "$username:$password"

//       tobyteArray转成byte数组，再转成base64
//        系统库提供Base64， 转成Base64字符串
        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")

        Debuger.printfLog("base64Str login $base64")
//      preference存储
        usernameStorage = username

        userBasicCodeStorage = base64

//     拿到token
        val loginService = getTokenObservable()
//
        val userService = userRepository.getPersonInfoObservable()
//
        val authorizations = Observable.zip(loginService, userService,
            BiFunction<String, User, User> { _, user ->
                user
            }).flatMap {

            FlatMapResult2Response(it)
        }

        RetrofitFactory.executeResult(authorizations, object : ResultProgressObserver<User>(context) {

            override fun onSuccess(result: User?) {
                passwordStorage = password
                token.value = true
            }

            override fun onCodeError(code: Int, message: String) {
                token.value = false
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                token.value = false
            }

        })



    }

    /**
     * 清除token
     */
    fun clearTokenStorage() {
        accessTokenStorage = ""
        userBasicCodeStorage = ""
    }


    /**
     * 退出登录
     */
    fun logout(context: Context) {
        accessTokenStorage = ""
        userBasicCodeStorage = ""
        userInfoStorage = ""
        val intent = Intent(context, StartNavigationActivity::class.java)
        intent.clearTask()
        context.startActivity(intent)
    }
}