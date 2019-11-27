package com.xxds.kotlin_git.kotlin.module.trend


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.shuyu.commonrecycler.BindSuperAdapterManager

import com.xxds.kotlin_git.R
import com.xxds.kotlin_git.databinding.FragmentTrendBinding
import com.xxds.kotlin_git.kotlin.model.ui.ReposUIModel
import com.xxds.kotlin_git.kotlin.module.ARouterAddress.ReposDetailActivity
import com.xxds.kotlin_git.kotlin.module.base.BaseListFragment
import com.xxds.kotlin_git.kotlin.ui.adapter.ListDropDownAdapter
import com.xxds.kotlin_git.kotlin.ui.holder.ReposHolder
import kotlinx.android.synthetic.main.fragment_trend.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TrendFragment : BaseListFragment<FragmentTrendBinding,TrendViewModel>() {

    private lateinit var baseRecycler: RecyclerView

    override fun getLayoutId(): Int {

        return  R.layout.fragment_trend
    }
    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)

        baseRecycler = RecyclerView(activity as Context)
        baseRecycler.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDropLists(context)
    }

    override fun onItemClick(context: Context, position: Int) {
        super.onItemClick(context, position)

        adapter?.dataList?.get(position).apply {

            val data = this as ReposUIModel
            ReposDetailActivity.gotoReposDetail(data.ownerName, data.repositoryName)
        }
    }
    override fun getViewModelClass(): Class<TrendViewModel> = TrendViewModel::class.java

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun getRecyclerView(): RecyclerView? = baseRecycler

    override fun bindHolder(manager: BindSuperAdapterManager) {

        manager.bind(ReposUIModel::class.java,ReposHolder.ID,ReposHolder::class.java)
    }

    /**
     * 初始化弹出过滤列表
     */
    private fun initDropLists(context: Context?) {

        val sortData = getViewModel().sortData
        val sortValue = getViewModel().sortValue

        val dropMap = HashMap<String, View>()

        for (i in 0 until sortData.size) {

            val dropList = ListView(context)
            dropList.dividerHeight = 0
            val sinceListAdapter = ListDropDownAdapter(context!!, sortData[i])
            dropList.adapter = sinceListAdapter
            dropMap[sortData[i][0]] = dropList
            dropList.setOnItemClickListener { view, _, p, _ ->
                (view.adapter as ListDropDownAdapter).setCheckItem(p)
                trend_drop_menu.setTabText(sortData[i][p])
                trend_drop_menu.closeMenu()
                getViewModel().sortType[i] = sortValue[i][p]
                showRefresh()
            }
        }


        trend_drop_menu.setDropDownMenu(dropMap.keys.toList(), dropMap.values.toList(), baseRecycler)
    }
}
