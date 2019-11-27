package com.xxds.kotlin_git.kotlin.service

import com.xxds.kotlin_git.kotlin.common.config.AppConfig
import io.reactivex.Notification
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

interface NotificationService {

    @GET("notifications")
    fun getNotification(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Query("all") all: Boolean,
        @Query("participating") participating: Boolean,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<com.xxds.kotlin_git.kotlin.model.bean.Notification>>>

    @GET("notifications")
    fun getNotificationUnRead(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<ArrayList<com.xxds.kotlin_git.kotlin.model.bean.Notification>>>

    @PATCH("notifications/threads/{threadId}")
    fun setNotificationAsRead(
        @Path("threadId") threadId: String): Observable<Response<ResponseBody>>


    @PUT("notifications")
    fun setAllNotificationAsRead(): Observable<Response<ResponseBody>>
}