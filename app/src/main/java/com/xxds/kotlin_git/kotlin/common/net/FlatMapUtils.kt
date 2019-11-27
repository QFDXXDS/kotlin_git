package com.xxds.kotlin_git.kotlin.common.net

import io.reactivex.ObservableSource
import io.reactivex.Observer
import retrofit2.Response

/**
 * 将Response转化为实体数据
 */
class FlatMapResponse2Result<T>(private val response: Response<T>) : ObservableSource<T> {


    override fun subscribe(observer: Observer<in T?>) {
        if (response.isSuccessful) {
            observer.onNext(response.body())
        } else {
            observer.onError(Throwable(response.code().toString(), Throwable(response.errorBody().toString())))
        }
    }


}

/**
 * 将实体数据转化为Response
 */
class FlatMapResult2Response<T>(private val t: T) : ObservableSource<Response<T>> {

    override fun subscribe(observer: Observer<in Response<T>?>) {
        observer.onNext(Response.success(t))
    }
}


/**
 * 不损失header的情况下转化Response中的 列表 数据
 */
class FlatMapResponse2ResponseResult<T>(private val response: Response<T>, private val conversionCallBack: FlatConversionInterface<T>) : ObservableSource<Response<ArrayList<Any>>> {
    override fun subscribe(observer: Observer<in Response<ArrayList<Any>>>) {
        if (response.isSuccessful) {
            val result = conversionCallBack.onConversion(response.body())
            observer.onNext(Response.success(result, response.headers()))

        } else {
            observer.onError(Throwable(response.code().toString(), Throwable(response.errorBody().toString())))
        }

    }
}



interface FlatConversionInterface<T> {
    fun onConversion(t: T?): ArrayList<Any>
}



