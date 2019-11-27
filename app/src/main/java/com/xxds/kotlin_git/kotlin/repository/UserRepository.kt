package com.xxds.kotlin_git.kotlin.repository

import android.app.Application
import android.content.Context
import com.xxds.kotlin_git.kotlin.common.config.AppConfig
import com.xxds.kotlin_git.kotlin.common.net.*
import com.xxds.kotlin_git.kotlin.common.utils.Debuger
import com.xxds.kotlin_git.kotlin.common.utils.GSYPreference
import com.xxds.kotlin_git.kotlin.model.AppGlobalModel
import com.xxds.kotlin_git.kotlin.model.bean.Event
import com.xxds.kotlin_git.kotlin.model.bean.Notification
import com.xxds.kotlin_git.kotlin.model.bean.User
import com.xxds.kotlin_git.kotlin.model.conversion.EventConversion
import com.xxds.kotlin_git.kotlin.model.conversion.UserConversion
import com.xxds.kotlin_git.kotlin.repository.dao.UserDao
import com.xxds.kotlin_git.kotlin.service.NotificationService
import com.xxds.kotlin_git.kotlin.service.RepoService
import com.xxds.kotlin_git.kotlin.service.UserService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class UserRepository @Inject constructor(private val retrofit: Retrofit, private val appGlobalModel: AppGlobalModel,
                                         private val application: Application, private val userDao: UserDao) {


    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    /**
     * 获取用户详细信息
     */
    fun getPersonInfoObservable(userName: String? = null): Observable<User> {

        val isLoginUser = userName == null
        //根据是否有用户名，获取第三方用户数据或者当前用户数据
        val userService = if (isLoginUser) {
            retrofit.create(UserService::class.java).getPersonInfo(true)
        } else {
            retrofit.create(UserService::class.java).getUser(true, userName!!)
        }

        return doUserInfoFlat(userService, isLoginUser)
    }

    /**
     * 户数据请求、组装、保存
     */
    private fun doUserInfoFlat(service: Observable<Response<User>>, isLoginUser: Boolean): Observable<User> {
        return service.flatMap {
            FlatMapResponse2Result(it)
        }.flatMap {
            ///获取用户star数
            val starredService = retrofit.create(RepoService::class.java).getStarredRepos(true, it.login!!, 1, "updated", 1)
            val honorService = retrofit.create(RepoService::class.java).getUserRepository100StatusDao(true, it.login!!, 1)
            val starResponse = starredService.blockingSingle()
            val honorResponse = honorService.blockingSingle()
            val starPageString = starResponse.headers().get("page_info")
            if (starPageString != null) {
                val pageInfo = GsonUtils.parserJsonToBean(starPageString, PageInfo::class.java)
                it.starRepos = if (pageInfo.last < 0) {
                    0
                } else {
                    pageInfo.last
                }
            }
            if (honorResponse.isSuccessful) {
                val list = honorResponse.body()
                var count = 0
                list?.forEach {
                    count += it.watchersCount
                }
                it.honorRepos = count
            }
            Observable.just(it)
        }.doOnNext {
            ///如果是登录用户，保存一份数据到 SharedPreferences
            if (isLoginUser) {
                ///保存用户信息
                userInfoStorage = GsonUtils.toJsonString(it)
                UserConversion.cloneDataFromUser(application, it, appGlobalModel.userObservable)
            }
            userDao.saveUserInfo(Response.success(it), it.login!!)
        }.onErrorResumeNext(Function<Throwable, Observable<User>> { t ->
            ///拦截错误
            //userInfoStorage = ""
            Debuger.printfLog("userInfo onErrorResumeNext ")
            Observable.error(t)
        })
    }



    /**
     * 获取用户接收到的事件
     */
    fun getReceivedEvent(resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {

//        获取用户登录状态
        val login = appGlobalModel.userObservable.login
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }

        val receivedEvent = retrofit.create(UserService::class.java)
            .getNewsEvent(true, username, page)
            .doOnNext {
//                userDao.saveReceivedEventDao(it, page == 1)
            }

        val userObserver = userDao.getReceivedEventDao()
            .doOnNext {
                if (page == 1) {
                    resultCallBack?.onCacheSuccess(it)
                }
            }

        val zipService = Observable.zip(userObserver, receivedEvent,
            BiFunction<ArrayList<Any>, Response<ArrayList<Event>>, Response<ArrayList<Event>>> { _, remote ->
                remote
            })

        userEventRequest(zipService, resultCallBack)
    }



    /**
     * 执行用事件相关请求
     */
    private fun userEventRequest(observer: Observable<Response<ArrayList<Event>>>, resultCallBack: ResultCallBack<ArrayList<Any>>?) {

        val service = observer
            .flatMap {
                FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Event>> {
                    override fun onConversion(t: ArrayList<Event>?): ArrayList<Any> {
                        val eventUIList = ArrayList<Any>()
                        t?.apply {
                            for (event in t) {
                                eventUIList.add(EventConversion.eventToEventUIModel(event))
                            }
                        }
                        return eventUIList
                    }
                })
            }
        RetrofitFactory.executeResult(service, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }


    /**
     * 获取用户信息
     */
    fun getPersonInfo(resultCallBack: ResultCallBack<User>?, resultEventCallBack: ResultCallBack<ArrayList<Any>>, userName: String? = null) {

        val userObserver = userDao.getUserInfoDao(userName)
            .doOnNext {
                if (it?.login != null) {
                    resultCallBack?.onCacheSuccess(it)
                }
            }.flatMap {
                ///根据用户类型，判断下一步是获取组织成员还是用户事件
                if (it.login != null) {
                    if (it.type == "Organization") {
                        userDao.getOrgMembersDao(it.login!!)
                    } else {
                        userDao.getUserEventDao(it.login!!)
                    }
                } else {
                    Observable.just(ArrayList())
                }
            }.doOnNext {
                resultEventCallBack.onCacheSuccess(it)
            }

        val mergeService = getPersonInfoObservable(userName)
            .flatMap {
                resultCallBack?.onSuccess(it)
                ///根据用户类型，判断下一步是获取组织成员还是用户事件
                if (it.type == "Organization") {
                    getOrgMembers(it.login!!, resultEventCallBack)
                    Observable.just(Response.success(ArrayList()))
                } else {
                    getUserEventObservable(it.login)
                }
            }

        val zipService = Observable.zip(userObserver, mergeService,
            BiFunction<ArrayList<Any>, Response<ArrayList<Event>>, Response<ArrayList<Event>>> { _, remote ->
                remote
            })

        userEventRequest(zipService, resultEventCallBack)
    }


    /**
     * 获取用户产生的事件
     */
    fun getOrgMembers(login: String?, resultCallBack: ResultCallBack<ArrayList<Any>>?, page: Int = 1) {
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }
        val userEvent = retrofit.create(UserService::class.java)
            .getOrgMembers(true, login ?: "", page)
            .doOnNext {
                userDao.saveOrgMembersDao(it, login!!, page == 1)
            }.flatMap {
                FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<User>> {
                    override fun onConversion(t: ArrayList<User>?): ArrayList<Any> {
                        val dataList = ArrayList<Any>()
                        t?.apply {
                            for (data in t) {
                                dataList.add(UserConversion.userToUserUIModel(data))
                            }
                        }
                        return dataList
                    }
                })
            }
        RetrofitFactory.executeResult(userEvent, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onCodeError(code: Int, message: String) {
                resultCallBack?.onFailure()
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }

        })
    }


    /**
     * 获取用户产生的行为事件
     */
    fun getUserEventObservable(userName: String?, page: Int = 1): Observable<Response<ArrayList<Event>>> {
        return retrofit.create(UserService::class.java)
            .getUserEvents(true, userName ?: "", page)
            .doOnNext {
                userDao.saveUserEventDao(it, userName!!, page == 1)
            }

    }

    /**
     * 获取用户产生的事件
     */
    fun getUserEvent(login: String?, resultCallBack: ResultCallBack<ArrayList<Any>>, page: Int = 1) {
        val username = login ?: ""
        if (username.isEmpty()) {
            return
        }
        val userEvent = getUserEventObservable(login, page)
        userEventRequest(userEvent, resultCallBack)
    }


    fun getNotify(all: Boolean?, participating: Boolean?, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {

        val service =
            if (all == null || participating == null) {
                retrofit.create(NotificationService::class.java).getNotificationUnRead(true, page)
            } else {
                retrofit.create(NotificationService::class.java).getNotification(true, all, participating, page)
            }

        val notifyService = service.flatMap {
            FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<Notification>> {
                override fun onConversion(t: ArrayList<Notification>?): ArrayList<Any> {
                    val dataList = ArrayList<Any>()
                    t?.apply {
                        for (item in t) {
                            dataList.add(EventConversion.notificationToEventUIModel(application, item))
                        }
                    }
                    return dataList
                }
            })
        }

        RetrofitFactory.executeResult(notifyService, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                super.onPageInfo(first, current, last)
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    fun setNotificationAsRead(id: String) {
        val service = retrofit.create(NotificationService::class.java).setNotificationAsRead(id)
        RetrofitFactory.executeResult(service, object : ResultTipObserver<ResponseBody>(application) {
            override fun onSuccess(result: ResponseBody?) {
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
            }
        })
    }


    fun setAllNotificationAsRead(context: Context) {
        val service = retrofit.create(NotificationService::class.java).setAllNotificationAsRead()
        RetrofitFactory.executeResult(service, object : ResultProgressObserver<ResponseBody>(context) {
            override fun onSuccess(result: ResponseBody?) {
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
            }
        })
    }
}