package com.xxds.kotlin_git.kotlin.repository.dao

import android.app.Application
import com.xxds.kotlin_git.kotlin.GSYGithubApplication
import com.xxds.kotlin_git.kotlin.common.db.*
import com.xxds.kotlin_git.kotlin.common.net.GsonUtils
import com.xxds.kotlin_git.kotlin.model.bean.TrendingRepoModel
import com.xxds.kotlin_git.kotlin.model.conversion.ReposConversion
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response
import java.lang.Appendable
import javax.inject.Inject

class ReposDao @Inject constructor(private  val application: Application) {

    /**
     * 获取趋势数据
     */
    fun getTrendDao(language: String, since: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
            .map {
                val list = FlatMapRealmReadList(it, object :
                    FlatRealmReadConversionInterface<TrendingRepoModel, TrendRepository> {
                    override fun query(realm: Realm): RealmResults<TrendRepository> {
                        return realm.where(TrendRepository::class.java).equalTo("languageType", language).equalTo("since", since).findAll()
                    }

                    override fun onJSON(t: TrendRepository): List<TrendingRepoModel> {
                        return GsonUtils.parserJsonToArrayBeans(t.data!!, TrendingRepoModel::class.java)
                    }

                    override fun onConversion(t: TrendingRepoModel): Any {
                        return ReposConversion.trendToReposUIModel(t)
                    }
                })
                list
            }
    }


    /**
     * 保存趋势数据
     */
    fun saveTrendDao(response: Response<String>, language: String, since: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, TrendRepository::class.java, object :
            FlatTransactionInterface<TrendRepository> {
            override fun query(q: RealmQuery<TrendRepository>): RealmResults<TrendRepository> {
                return q.equalTo("languageType", language).equalTo("since", since).findAll()
            }

            override fun onTransaction(targetObject: TrendRepository?) {
                targetObject?.data = response.body()
                targetObject?.languageType = language
                targetObject?.since = since
            }
        }, needSave)
    }

}