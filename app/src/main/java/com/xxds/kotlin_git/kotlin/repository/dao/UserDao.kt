package com.xxds.kotlin_git.kotlin.repository.dao

import android.app.Application
import com.xxds.kotlin_git.kotlin.common.db.*
import com.xxds.kotlin_git.kotlin.common.net.GsonUtils
import com.xxds.kotlin_git.kotlin.model.bean.Event
import com.xxds.kotlin_git.kotlin.model.bean.User
import com.xxds.kotlin_git.kotlin.model.conversion.EventConversion
import com.xxds.kotlin_git.kotlin.model.conversion.UserConversion
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.annotations.Index
import retrofit2.Response
import javax.inject.Inject

class UserDao @Inject constructor(private val application: Application) {

    /**
     * 保存当前用户接收到的事件
     */
   fun saveReceivedEventDao(response: Response<ArrayList<Event>>, needSave: Boolean) {
        FlatMapRealmSaveResult(response, ReceivedEvent::class.java, object : FlatTransactionInterface<ReceivedEvent> {
            override fun query(q: RealmQuery<ReceivedEvent>): RealmResults<ReceivedEvent> {
                return q.findAll()
            }

            override fun onTransaction(targetObject: ReceivedEvent?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.data = data
            }
        }, needSave)
    }


    /**
     * 保存组织的成员信息
     */
    fun saveUserInfo(response: Response<User>, userName: String) {
//        FlatMapRealmSaveResult(response, UserInfo::class.java, object : FlatTransactionInterface<UserInfo> {
//            override fun query(q: RealmQuery<UserInfo>): RealmResults<UserInfo> {
//                return q.equalTo("userName", userName).findAll()
//            }
//
//            override fun onTransaction(targetObject: UserInfo?) {
//                val data = GsonUtils.toJsonString(response.body())
//                targetObject?.userName = userName
//                targetObject?.data = data
//            }
//        }, true)
    }


    /**
     * 获取当前用户接收到的事件
     */
    fun getReceivedEventDao(): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
            .map {
                val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Event, ReceivedEvent> {
                    override fun query(realm: Realm): RealmResults<ReceivedEvent> {
                        return realm.where(ReceivedEvent::class.java).findAll()
                    }

                    override fun onJSON(t: ReceivedEvent): List<Event> {
                        return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                    }

                    override fun onConversion(t: Event): Any {
                        return EventConversion.eventToEventUIModel(t)
                    }
                })
                list
            }
    }

    /**
     * 获取用户的信息
     */
    fun getUserInfoDao(userName: String?): Observable<User?> {
        return RealmFactory.getRealmObservable()
            .map {
                val result = it.where(UserInfo::class.java).equalTo("userName",
                    userName ?: "").findAll()
                val item = if (result.isEmpty()) {
                    User()
                } else {
                    GsonUtils.parserJsonToBean(result[0]!!.data!!, User::class.java)
                }
                item
            }
    }


    /**
     * 获取组织的成员信息
     */
    fun getOrgMembersDao(userName: String?): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
            .map {
                val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<User, OrgMember> {
                    override fun query(realm: Realm): RealmResults<OrgMember> {
                        return realm.where(OrgMember::class.java).equalTo("org", userName).findAll()
                    }

                    override fun onJSON(t: OrgMember): List<User> {
                        return GsonUtils.parserJsonToArrayBeans(t.data!!, User::class.java)
                    }

                    override fun onConversion(t: User): Any {
                        return UserConversion.userToUserUIModel(t)
                    }
                })
                list
            }
    }

    /**
     * 获取用户的行为信息
     */
    fun getUserEventDao(userName: String): Observable<ArrayList<Any>> {
        return RealmFactory.getRealmObservable()
            .map {
                val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<Event, UserEvent> {
                    override fun query(realm: Realm): RealmResults<UserEvent> {
                        return realm.where(UserEvent::class.java).equalTo("userName", userName).findAll()
                    }

                    override fun onJSON(t: UserEvent): List<Event> {
                        return GsonUtils.parserJsonToArrayBeans(t.data!!, Event::class.java)
                    }

                    override fun onConversion(t: Event): Any {
                        return EventConversion.eventToEventUIModel(t)
                    }
                })
                list
            }
    }

    /**
     * 保存用户的信息
     */
    fun saveOrgMembersDao(response: Response<ArrayList<User>>, userName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, OrgMember::class.java, object : FlatTransactionInterface<OrgMember> {
            override fun query(q: RealmQuery<OrgMember>): RealmResults<OrgMember> {
                return q.equalTo("org", userName).findAll()
            }

            override fun onTransaction(targetObject: OrgMember?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.org = userName
                targetObject?.data = data
            }
        }, needSave)
    }


    /**
     * 保存用户的行为信息
     */
    fun saveUserEventDao(response: Response<ArrayList<Event>>, userName: String, needSave: Boolean) {
        FlatMapRealmSaveResult(response, UserEvent::class.java, object : FlatTransactionInterface<UserEvent> {
            override fun query(q: RealmQuery<UserEvent>): RealmResults<UserEvent> {
                return q.equalTo("userName", userName).findAll()
            }

            override fun onTransaction(targetObject: UserEvent?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.userName = userName
                targetObject?.data = data
            }
        }, needSave)
    }

}