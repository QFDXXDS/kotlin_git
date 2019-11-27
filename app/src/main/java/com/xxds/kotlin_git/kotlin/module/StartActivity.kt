package com.xxds.kotlin_git.kotlin.module

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xxds.kotlin_git.R
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.startActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val intent = Intent(this,StartNavigationActivity::class.java)
        intent.clearTask()
        startActivity(intent)
        finish()

        Runtime.getRuntime().totalMemory()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {


    }



}
