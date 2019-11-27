package com.xxds.kotlin_git.kotlin.repository

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.common.net.*
import com.xxds.kotlin_git.kotlin.model.bean.Repository
import com.xxds.kotlin_git.kotlin.model.conversion.ReposConversion
import com.xxds.kotlin_git.kotlin.model.conversion.TrendConversion
import com.xxds.kotlin_git.kotlin.repository.dao.ReposDao
import com.xxds.kotlin_git.kotlin.service.RepoService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RepoRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application, private val reposDao: ReposDao) {

    companion object {
        const val STAR_KEY = "starred"
        const val WATCH_KEY = "watched"
    }


    /**
     * 趋势
     * @param language 语言
     * @param since 时间（今天/本周/本月）
     */
    fun getTrend(resultCallBack: ResultCallBack<ArrayList<Any>>, language: String, since: String) {

        val dbService = reposDao.getTrendDao(language, since)
            .doOnNext {
                resultCallBack.onCacheSuccess(it)
            }


        val trendService = retrofit.create(RepoService::class.java)
            .getTrendData(true, language, since)
            .flatMap {
                FlatMapResponse2Result(it)
            }.map {
                TrendConversion.htmlToRepo(it)
            }.doOnNext {
                reposDao.saveTrendDao(Response.success(GsonUtils.toJsonString(it)), language, since, true)
            }.map {
                val dataUIList = ArrayList<Any>()
                for (reposUi in it) {
                    dataUIList.add(ReposConversion.trendToReposUIModel(reposUi))
                }
                dataUIList
            }.flatMap {
                FlatMapResult2Response(it)
            }


        val zipService = Observable.zip(dbService, trendService,
            BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                remote
            })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {
            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack.onFailure()
            }
        })

    }


    /**
     * 获取当前用户对仓库状态
     */
    fun getReposStatus(userName: String, reposName: String, resultCallBack: ResultCallBack<HashMap<String, Boolean>>?) {
        val starredService = retrofit.create(RepoService::class.java).checkRepoStarred(userName, reposName)
            .flatMap {
                val starred = if (it.code() == 404) {
                    false
                } else it.isSuccessful
                Observable.just(starred)
            }
        val watchedService = retrofit.create(RepoService::class.java).checkRepoWatched(userName, reposName)
            .flatMap {
                val watched = if (it.code() == 404) {
                    false
                } else it.isSuccessful
                Observable.just(watched)
            }

        val statusService = Observable.zip(starredService, watchedService,
            BiFunction<Boolean, Boolean, HashMap<String, Boolean>> { starred, watched ->
                val map = HashMap<String, Boolean>()
                map[STAR_KEY] = starred
                map[WATCH_KEY] = watched
                map
            })
            .flatMap {
                FlatMapResult2Response(it)
            }
        RetrofitFactory.executeResult(statusService, object : ResultObserver<HashMap<String, Boolean>>() {

            override fun onSuccess(result: HashMap<String, Boolean>?) {
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
     * 改变当前用户对仓库的Star状态
     */
    fun changeStarStatus(context: Context, userName: String, reposName: String, status: MutableLiveData<Boolean>) {
        val reposService = retrofit.create(RepoService::class.java)
        val starred = status.value ?: return
        val starredStatus = if (starred) {
            reposService.unstarRepo(userName, reposName)
        } else {
            reposService.starRepo(userName, reposName)
        }
        RetrofitFactory.executeResult(starredStatus, object : ResultProgressObserver<ResponseBody>(context) {

            override fun onSuccess(result: ResponseBody?) {
                status.value = status.value?.not()
            }

            override fun onCodeError(code: Int, message: String) {

            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

            }

        })
    }

    /**
     * 改变当前用户对仓库的订阅状态
     */
    fun changeWatchStatus(context: Context, userName: String, reposName: String, status: MutableLiveData<Boolean>) {
        val reposService = retrofit.create(RepoService::class.java)
        val watched = status.value ?: return
        val watchedStatus = if (watched) {
            reposService.unwatchRepo(userName, reposName)
        } else {
            reposService.watchRepo(userName, reposName)
        }
        RetrofitFactory.executeResult(watchedStatus, object : ResultProgressObserver<ResponseBody>(context) {

            override fun onSuccess(result: ResponseBody?) {
                status.value = status.value?.not()
            }

            override fun onCodeError(code: Int, message: String) {

            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {

            }

        })
    }

    fun forkRepository(context: Context, userName: String, reposName: String) {
        val reposService = retrofit.create(RepoService::class.java)
            .createFork(userName, reposName)
        RetrofitFactory.executeResult(reposService, object : ResultProgressObserver<Repository>(context) {

            override fun onSuccess(result: Repository?) {
                context.toast(R.string.forkSuccess)
            }

            override fun onCodeError(code: Int, message: String) {
                context.toast(R.string.forkFail)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                context.toast(R.string.forkFail)
            }
        })
    }


}