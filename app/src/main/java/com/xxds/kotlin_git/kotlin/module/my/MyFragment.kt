package com.xxds.kotlin_git.kotlin.module.my

import android.arch.lifecycle.Observer
import android.content.Intent
import android.view.View
import com.xxds.kotlin_git.databinding.LayoutUserHeaderBinding
import com.xxds.kotlin_git.kotlin.module.base.BaseUserInfoFragment
import com.xxds.kotlin_git.kotlin.module.notify.NotifyActivity

class MyFragment: BaseUserInfoFragment<MyViewModel>() {

    override fun getLoginName(): String? = null

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun bindHeader(binding: LayoutUserHeaderBinding) {

        binding.userHeaderNotify.visibility = View.VISIBLE
        binding.userHeaderNotify.setOnClickListener {
            startActivityForResult(Intent(context, NotifyActivity::class.java), 1000)
        }
        getViewModel().notifyColor.observe(this, Observer { result ->
            result?.apply {
                binding.userHeaderNotify.setTextColor(this)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showRefresh()
    }

}