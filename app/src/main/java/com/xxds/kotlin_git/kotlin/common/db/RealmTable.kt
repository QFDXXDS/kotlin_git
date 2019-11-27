package com.xxds.kotlin_git.kotlin.common.db

import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * 仓库活跃事件表
 */
@RealmClass
open class RepositoryEvent : RealmObject() {
    open var fullName: String? = null
    open var data: String? = null
}
/**
 * 用户接受事件表
 */
@RealmClass
open class ReceivedEvent : RealmObject() {
    open var data: String? = null
}
/**
 * 趋势表
 */
@RealmClass
open class TrendRepository : RealmObject() {
    open var languageType: String? = null
    open var data: String? = null
    open var since: String? = null
}

/**
 * 用户表
 */
@RealmClass
open class UserInfo : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

/**
 * 用户关注表
 */
@RealmClass
open class OrgMember : RealmObject() {
    open var org: String? = null
    open var data: String? = null
}

/**
 * 用户动态表
 */
@RealmClass
open class UserEvent : RealmObject() {
    open var userName: String? = null
    open var data: String? = null
}

