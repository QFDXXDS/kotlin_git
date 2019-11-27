package com.xxds.kotlin_git.kotlin.module.main

import com.xxds.kotlin_git.R
import org.jetbrains.anko.toast


class MainExitLogic(private  val activity: MainActivity) {

    var firstTime = 0L

    fun backPress() {

        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 200) {
            activity.toast(R.string.doublePressExit)
            firstTime = secondTime
            return
        } else {

            System.exit(0)
        }
    }

}