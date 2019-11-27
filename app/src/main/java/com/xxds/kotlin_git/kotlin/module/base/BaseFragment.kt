package com.xxds.kotlin_git.kotlin.module.base


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.kotlin.di.Injectable
import com.xxds.kotlin_git.kotlin.ui.holder.base.GSYDataBindingComponent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(), Injectable {

    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<T>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            false,
            GSYDataBindingComponent())

        onCreateView(binding?.root)
        return binding?.root
    }

    open fun actionOpenByBrowser() {

    }

    open fun actionCopy() {

    }

    open fun actionShare() {

    }

    abstract fun onCreateView(mainView: View?)

    abstract fun getLayoutId(): Int

    /**
     * Navigation 的页面跳转
     */
    fun navigationPopUpTo(view: View, args: Bundle?, actionId: Int, finishStack: Boolean) {
//      获取view的navcontroller
        val controller = Navigation.findNavController(view)
//      navcontroller控制跳转

        controller.navigate(actionId,
            args, NavOptions.Builder().setPopUpTo(controller.graph.id, true).build())

        if (finishStack) {

//           fragment关联的activity
            activity?.finish()
        }
    }

    fun enterFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    fun exitFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }
}