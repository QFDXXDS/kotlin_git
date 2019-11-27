package com.xxds.kotlin_git.kotlin.di

import android.app.Application
import com.xxds.kotlin_git.kotlin.GSYGithubApplication
import com.xxds.kotlin_git.kotlin.common.db.RealmFactory
import com.xxds.kotlin_git.kotlin.common.net.RetrofitFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * App级别注入
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindModule::class
])
interface AppComponent {

    //  如果你想在绑定component时注入参数，如app需要一个用户名参数，就可以给component的builder方法添加一个[@BindsInstance][BindsInstance]方法注解以使用户名字符串实例可以被注入到component中：
    @Component.Builder   //自己编写AppComponent的构造方法
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(gsyGithubApplication: GSYGithubApplication)
}

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return RetrofitFactory.instance.retrofit
    }

    @Singleton
    @Provides
    fun providerRealmFactory(): RealmFactory {
        return RealmFactory.instance
    }
}


