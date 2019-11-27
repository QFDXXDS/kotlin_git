package com.xxds.kotlin_git.kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.mikepenz.iconics.Iconics
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.shuyu.gsygiideloader.GSYGlideImageLoader
import com.shuyu.gsyimageloader.GSYImageLoaderManager
import com.tencent.bugly.crashreport.CrashReport
import com.xxds.kotlin_git.BuildConfig
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.common.db.RealmFactory
import com.xxds.kotlin_git.kotlin.common.style.GSYIconfont
import com.xxds.kotlin_git.kotlin.common.utils.CommonUtils
import com.xxds.kotlin_git.kotlin.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject
import kotlin.properties.Delegates

class GSYGithubApplication: Application(),HasActivityInjector {

    companion object {
        var instance: GSYGithubApplication by Delegates.notNull()
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {

            ARouter.openDebug()
            ARouter.openLog()

        } else {

            CrashReport.initCrashReport(applicationContext,"",false)
        }
        ARouter.init(this)
        AppInjector.init(this)
        ///初始化图标库
        Iconics.init(applicationContext)
        Iconics.registerFont(GSYIconfont())
//       数据库
        Realm.init(applicationContext)
        RealmFactory.instance

        GSYImageLoaderManager.initialize(GSYGlideImageLoader(this))

        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {

            override fun placeholder(ctx: Context?): Drawable {

                return getDrawable(R.drawable.logo)
            }

            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?, tag: String?) {

                CommonUtils.loadUserHeaderImage(imageView!!,uri.toString())
            }
        })


    }

    override fun activityInjector(): AndroidInjector<Activity> {

        return dispatchingAndroidInjector
    }


}