package com.xxds.kotlin_git.kotlin.service

import com.xxds.kotlin_git.kotlin.model.bean.AccessToken
import com.xxds.kotlin_git.kotlin.model.bean.LoginRequestModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
//  @Body 可以传对象
    fun authorizations(@Body authRequestModel: LoginRequestModel): Observable<Response<AccessToken>>

}